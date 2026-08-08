package com.tengwar.klavye

import android.annotation.SuppressLint
import android.inputmethodservice.InputMethodService
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.FrameLayout
import java.util.concurrent.CountDownLatch

/**
 * Türkçe Tengwar-Quenya sistem klavyesi (v3 — WebView tabanlı).
 *
 * Önceki sürüm klavyeyi native Android View'larla (TextView/LinearLayout vb.)
 * elle yeniden çiziyordu; bu, tarayıcıdaki HTML/CSS önizlemesiyle ASLA piksel
 * piksel aynı olamazdı (iki tamamen farklı çizim sistemi). Bunun yerine artık
 * klavye, sohbette birlikte geliştirdiğimiz AYNI HTML/CSS/JS dosyasını
 * (assets/keyboard.html — tengwar-android-onizleme.html'in "üretim" sürümü)
 * bir WebView içinde çalıştırıyor. Görünüm, geçişler, popup'lar, fontlar
 * artifact'teki ile birebir aynı koddan geliyor. HTML/JS tarafı, gerçek
 * cihazda olduğunu şu köprü (window.AndroidKeyboard) üzerinden anlıyor ve
 * hedef uygulamaya doğrudan yazıyor.
 *
 * Genişlik: WebView, IME'nin kendisine ayırdığı tam ekran genişliğini
 * kullanıyor; klavyenin kendi JS'i (equalizeKeyWidths) tuş genişliklerini bu
 * gerçek genişliğe göre hesaplıyor — bu sayede farklı telefon/tablet ekran
 * boyutlarına otomatik uyum sağlanıyor.
 */
class TengwarIME : InputMethodService() {

    private val handler = Handler(Looper.getMainLooper())
    private var webView: WebView? = null

    override fun onCreateInputView(): View {
        val container = FrameLayout(this)
        val wv = createWebView()
        webView = wv
        container.addView(
            wv,
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(320))
        )
        return container
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        // Her yeni giriş alanına geçildiğinde klavyeyi baştan yükleyip
        // (mod/sayfa durumunu sıfırlayıp) temiz başlıyoruz.
        if (!restarting) {
            webView?.loadUrl("file:///android_asset/keyboard.html")
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    private fun createWebView(): WebView {
        val wv = WebView(this)
        wv.settings.javaScriptEnabled = true
        wv.settings.allowFileAccess = true
        wv.setBackgroundColor(android.graphics.Color.parseColor("#1b2e24"))
        wv.addJavascriptInterface(JsBridge(), "AndroidKeyboard")
        wv.loadUrl("file:///android_asset/keyboard.html")
        return wv
    }

    /** WebView içindeki JavaScript'in çağırdığı köprü. Bu metodlar WebView'in
     *  kendi arka plan thread'inde çalışır; InputConnection işlemlerini ana
     *  thread'e (UI thread) taşımamız gerekiyor. */
    private inner class JsBridge {

        @JavascriptInterface
        fun commitText(text: String, plain: Boolean) {
            handler.post { currentInputConnection?.commitText(text, 1) }
        }

        @JavascriptInterface
        fun deleteBackward() {
            handler.post { currentInputConnection?.deleteSurroundingText(1, 0) }
        }

        /** Tuşa basınca kısa bir titreşim verir. NOT: View.performHapticFeedback ilk denemede
         *  kullanıldı ama IME pencerelerinde (pencere odağı normal uygulama pencerelerinden
         *  farklı olduğu için) bazı cihazlarda hiç çalışmadığı görüldü — diğer klavye
         *  uygulamalarının da bu yüzden doğrudan Vibrator kullandığı anlaşılıyor. */
        @JavascriptInterface
        fun vibrate() {
            handler.post {
                val vibrator: android.os.Vibrator? =
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                        val vm = getSystemService(VIBRATOR_MANAGER_SERVICE) as? android.os.VibratorManager
                        vm?.defaultVibrator
                    } else {
                        @Suppress("DEPRECATION")
                        getSystemService(VIBRATOR_SERVICE) as? android.os.Vibrator
                    }
                if (vibrator?.hasVibrator() == true) {
                    // NOT: Hazır efektler (ör. EFFECT_TICK) marka/cihaz donanımına göre
                    // desteklenmeyebiliyor ve sessizce hiçbir şey yapmayabiliyor — bunun yerine
                    // her cihazda güvenilir çalışan basit bir titreşim kullanıyoruz.
                    // Tek uzun titreşim yerine KISA ÇİFT darbe (255 = maksimum yoğunluk, API
                    // tavanı) veriyoruz — aynı toplam güçte olsa da, açılma/kapanma geçişleri
                    // olduğu için elde çok daha belirgin hissediliyor.
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        val timings = longArrayOf(15, 10, 15)
                        val amplitudes = intArrayOf(255, 0, 255)
                        vibrator.vibrate(android.os.VibrationEffect.createWaveform(timings, amplitudes, -1))
                    } else {
                        @Suppress("DEPRECATION")
                        vibrator.vibrate(longArrayOf(0, 15, 10, 15), -1)
                    }
                }
            }
        }

        /** Tuşa basınca sistemin standart tuş tıklama sesini çalar. AudioManager,
         *  kullanıcının sistem ayarlarındaki "Dokunma sesleri" tercihine otomatik uyar. */
        @JavascriptInterface
        fun playClick() {
            handler.post {
                val am = getSystemService(AUDIO_SERVICE) as? android.media.AudioManager
                am?.playSoundEffect(android.media.AudioManager.FX_KEYPRESS_STANDARD)
            }
        }

        /** Sistemin klavye seçici (input method picker) penceresini açar. */
        @JavascriptInterface
        fun showKeyboardPicker() {
            handler.post {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                imm.showInputMethodPicker()
            }
        }

        /** Metni sistemin panosuna güvenilir şekilde kopyalar (native Android ClipboardManager). */
        @JavascriptInterface
        fun copyToClipboard(text: String) {
            handler.post {
                val cm = getSystemService(CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                cm?.setPrimaryClip(android.content.ClipData.newPlainText("Tengwar", text))
            }
        }

        /** Hedef uygulamada o an seçili metni okuyup Arşiv'e ekler.
         *  ÖNEMLİ: Bu artık ASENKRON — ana iş parçacığını (CountDownLatch ile)
         *  bekletmiyor, çünkü hedef kendi uygulamamızın WebView'ı (Defter
         *  sekmesi) olduğunda bu bekleme donma hissi yaratıp sonunda
         *  başarısız dönebiliyordu. Sonuç, JS tarafına geri çağrı ile
         *  (window.onArchiveSaveResult) bildiriliyor. */
        @JavascriptInterface
        fun saveSelectedToArchive() {
            handler.post {
                val ic = currentInputConnection
                // getSelectedText/getExtractedText, WebView'ın kendi textarea'sı gibi
                // bazı hedeflerde güvenilir çalışmıyor (bilinen bir platform sınırlaması).
                // Bunun yerine EVRENSEL olarak desteklenen sistem "Kopyala" eylemini
                // tetikleyip sonucu panodan okuyoruz. ÖNEMLİ: panoya KENDİMİZ yazmıyoruz
                // (ne sentinel ne de geri yükleme) — Android, panoya her setPrimaryClip
                // çağrısında kendi "Panoya kopyalandı" bildirimini gösteriyor; biz sadece
                // kopyalama eyleminden ÖNCEKİ ve SONRAKİ pano içeriğini KARŞILAŞTIRARAK
                // gerçekten yeni bir şey kopyalanıp kopyalanmadığını anlıyoruz.
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val previousText = clipboard.primaryClip?.let {
                    if (it.itemCount > 0) it.getItemAt(0).text?.toString() else null
                }
                ic?.performContextMenuAction(android.R.id.copy)
                handler.postDelayed({
                    val clip = clipboard.primaryClip
                    val copied = if (clip != null && clip.itemCount > 0) clip.getItemAt(0).text?.toString() else null
                    val selected = if (!copied.isNullOrBlank() && copied != previousText) copied else null
                    var saved = false
                    if (!selected.isNullOrBlank()) {
                        val words = archiveWords()
                        if (!words.contains(selected)) {
                            words.add(selected)
                            if (words.size > 20) words.removeAt(0) // kelime sınırı 20
                            saveArchiveWordsList(words)
                        }
                        saved = true
                        // Arşivden silinse bile kalıcı olması için AYRI bir listeye de ekleniyor.
                        addToJournal(selected)
                    }
                    webView?.evaluateJavascript("window.onArchiveSaveResult && window.onArchiveSaveResult($saved)", null)
                }, 150)
            }
        }

        /** Kayıtlı arşiv kelimelerini basit bir JSON dizisi (["kelime1","kelime2",...]) olarak döner. */
        @JavascriptInterface
        fun getArchiveWordsJson(): String {
            var json = "[]"
            runOnUiThreadBlocking {
                val words = archiveWords()
                json = "[" + words.joinToString(",") { jsonStringLiteral(it) } + "]"
            }
            return json
        }

        /** Sürükle-bırak ile değişen sıralamayı kalıcı olarak kaydeder (JS'ten JSON string dizisi gelir). */
        @JavascriptInterface
        fun saveArchiveOrder(orderJson: String) {
            handler.post {
                val order = parseJsonStringArray(orderJson)
                saveArchiveWordsList(order)
            }
        }

        /** Tek bir kelimeyi arşivden kalıcı olarak siler. */
        @JavascriptInterface
        fun deleteArchiveWord(word: String) {
            handler.post {
                val words = archiveWords()
                words.remove(word)
                saveArchiveWordsList(words)
            }
        }

        /** Arşivdeki TÜM kayıtlı sözcükleri kalıcı olarak siler. */
        @JavascriptInterface
        fun clearArchive() {
            handler.post {
                saveArchiveWordsList(mutableListOf())
            }
        }
    }

    /** Basit bir JSON string dizisi ayrıştırıcı (["a","b"] biçiminde) — dış kütüphaneye ihtiyaç duymadan. */
    private fun parseJsonStringArray(json: String): MutableList<String> {
        val result = mutableListOf<String>()
        val sb = StringBuilder()
        var inString = false
        var i = 0
        while (i < json.length) {
            val c = json[i]
            if (inString) {
                if (c == '\\' && i + 1 < json.length) {
                    val next = json[i + 1]
                    when (next) {
                        '"' -> sb.append('"')
                        '\\' -> sb.append('\\')
                        'n' -> sb.append('\n')
                        'r' -> sb.append('\r')
                        't' -> sb.append('\t')
                        else -> sb.append(next)
                    }
                    i += 2
                    continue
                } else if (c == '"') {
                    result.add(sb.toString())
                    sb.clear()
                    inString = false
                } else {
                    sb.append(c)
                }
            } else if (c == '"') {
                inString = true
            }
            i++
        }
        return result
    }

    private fun jsonStringLiteral(s: String): String {
        val sb = StringBuilder("\"")
        for (c in s) {
            when (c) {
                '\\' -> sb.append("\\\\")
                '"' -> sb.append("\\\"")
                '\n' -> sb.append("\\n")
                '\r' -> sb.append("\\r")
                else -> sb.append(c)
            }
        }
        sb.append("\"")
        return sb.toString()
    }

    // ---- Arşiv depolama (SharedPreferences, "||" ile ayrılmış tek metin) ----

    private fun archiveWords(): MutableList<String> {
        val raw = getSharedPreferences("tengwar_archive", MODE_PRIVATE).getString("words", "") ?: ""
        return if (raw.isEmpty()) mutableListOf() else raw.split("||").filter { it.isNotEmpty() }.toMutableList()
    }

    private fun saveArchiveWordsList(words: List<String>) {
        getSharedPreferences("tengwar_archive", MODE_PRIVATE).edit()
            .putString("words", words.joinToString("||"))
            .apply()
    }

    // ---- Günlük depolama (SharedPreferences, "word‡favorite" formatında, "||" ile ayrılmış) ----
    // Arşivden BAĞIMSIZ ve KALICIDIR — arşivden silinse bile günlükten silinmez.
    // SetupActivity.kt AYNI SharedPreferences anahtarını ("tengwar_journal") okuyup
    // yazıyor, böylece klavyede arşive eklenen her şey uygulamadaki Günlük sekmesinde
    // görünüyor (IME ve Activity aynı paket içinde olduğu için SharedPreferences ortak).
    private fun addToJournal(word: String) {
        val prefs = getSharedPreferences("tengwar_journal", MODE_PRIVATE)
        val raw = prefs.getString("entries", "") ?: ""
        val entries = if (raw.isEmpty()) mutableListOf() else raw.split("||").filter { it.isNotEmpty() }.toMutableList()
        entries.add("$word‡0")
        if (entries.size > 500) { // kelime sınırı 500
            while (entries.size > 500) entries.removeAt(0)
        }
        prefs.edit().putString("entries", entries.joinToString("||")).apply()
    }

    /** JsBridge metodları WebView'in arka plan thread'inde çalıştığı için,
     *  InputConnection/SharedPreferences işlemlerini burada ana thread'e
     *  taşıyıp senkron şekilde bekliyoruz (JS tarafı bir dönüş değeri bekliyor). */
    private fun runOnUiThreadBlocking(block: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            block()
            return
        }
        val latch = CountDownLatch(1)
        handler.post {
            try { block() } finally { latch.countDown() }
        }
        latch.await()
    }

    private fun dp(value: Int): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), resources.displayMetrics
    ).toInt()
}
