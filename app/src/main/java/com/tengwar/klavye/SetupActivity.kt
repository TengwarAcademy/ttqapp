package com.tengwar.klavye

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.webkit.JavascriptInterface
import android.webkit.WebView

/**
 * Uygulamanın açılış ekranı — artık sohbette birlikte tasarladığımız
 * "Tengwar Academy" arayüzünü (Ana Sayfa / Rehber / Klavye / Hakkında
 * sekmeleri) AYNI HTML/CSS/JS kodundan (assets/setup_screen.html) bir
 * WebView içinde gösteriyor. Klavyede kullandığımız yaklaşımın aynısı:
 * görünüm artifact'teki ile birebir aynı olsun diye native View yerine
 * WebView kullanıyoruz.
 *
 * window.AndroidSetup köprüsü, sayfanın gerçek klavye durumunu okumasını
 * ve sistemin klavye ayarları / klavye seçici penceresini açmasını sağlıyor.
 */
class SetupActivity : Activity() {

    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Android'in varsayılan başlık çubuğu artık manifest'teki NoActionBar
        // temasıyla zaten kaldırılıyor — arayüzün tamamı kendi yeşil/altın
        // temamızı kullanıyor. Durum çubuğunu da temaya uygun koyulaştırıyoruz.
        window.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.parseColor("#1b2e24")))
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.parseColor("#1b2e24")
        }

        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.setBackgroundColor(android.graphics.Color.parseColor("#1b2e24"))
        webView.addJavascriptInterface(JsBridge(), "AndroidSetup")
        webView.isFocusable = true
        webView.isFocusableInTouchMode = true
        webView.loadUrl("file:///android_asset/setup_screen.html")

        setContentView(webView)
        webView.requestFocus()
    }

    override fun onResume() {
        super.onResume()
        // Kullanıcı sistem ayarlarından (klavyeyi etkinleştir/seç) uygulamaya
        // geri döndüğünde sayfanın kendi "visibilitychange" dinleyicisi zaten
        // durumu tazeliyor; ekstra bir şey yapmaya gerek yok.
    }

    private inner class JsBridge {

        @JavascriptInterface
        fun openInputMethodSettings() {
            runOnUiThread {
                startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
            }
        }

        @JavascriptInterface
        fun showKeyboardPicker() {
            runOnUiThread {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showInputMethodPicker()
            }
        }

        /** Klavyemiz, sistemde etkinleştirilmiş klavyeler listesinde mi? */
        @JavascriptInterface
        fun isKeyboardEnabled(): Boolean {
            val enabled = Settings.Secure.getString(
                contentResolver, Settings.Secure.ENABLED_INPUT_METHODS
            ) ?: return false
            return enabled.contains(packageName)
        }

        /** Klavyemiz, o an sistemde SEÇİLİ (varsayılan) giriş yöntemi mi? */
        @JavascriptInterface
        fun isKeyboardSelected(): Boolean {
            val current = Settings.Secure.getString(
                contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD
            ) ?: return false
            return current.contains(packageName)
        }

        /** Base64 olarak gelen görseli (JPEG/PNG), telefonun İndirilenler klasörüne
         *  KAYDEDER — WebView'ın desteklemediği <a download> yerine bunu kullanıyoruz.
         *  Dosya kaydedilir ama AÇILMAZ; sadece "true/false" başarı durumu döner. */
        @JavascriptInterface
        fun saveFileToDownloads(base64Data: String, filename: String, mimeType: String): Boolean {
            return try {
                val bytes = android.util.Base64.decode(base64Data, android.util.Base64.DEFAULT)
                val resolver = contentResolver
                val values = android.content.ContentValues().apply {
                    put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(android.provider.MediaStore.MediaColumns.MIME_TYPE, mimeType)
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                        put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
                    }
                }
                val collection = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI
                } else {
                    android.provider.MediaStore.Files.getContentUri("external")
                }
                val uri = resolver.insert(collection, values)
                if (uri != null) {
                    resolver.openOutputStream(uri)?.use { it.write(bytes) }
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                false
            }
        }

        // ---- Günlük (Journal) — klavyenin (TengwarIME) yazdığı AYNI SharedPreferences
        // anahtarını ("tengwar_journal") okuyup yazıyoruz. Format: "kelime‡0veya1||..." */

        private fun readJournalEntries(): MutableList<Pair<String, Boolean>> {
            val raw = getSharedPreferences("tengwar_journal", MODE_PRIVATE).getString("entries", "") ?: ""
            if (raw.isEmpty()) return mutableListOf()
            return raw.split("||").filter { it.isNotEmpty() }.map { entry ->
                val parts = entry.split("‡")
                val word = parts.getOrElse(0) { "" }
                val fav = parts.getOrElse(1) { "0" } == "1"
                word to fav
            }.toMutableList()
        }

        private fun writeJournalEntries(entries: List<Pair<String, Boolean>>) {
            val raw = entries.joinToString("||") { (word, fav) -> "$word‡${if (fav) "1" else "0"}" }
            getSharedPreferences("tengwar_journal", MODE_PRIVATE).edit().putString("entries", raw).apply()
        }

        /** Günlükteki tüm kayıtları [{"word":"...","favorite":true/false}, ...] JSON'u olarak döner. */
        @JavascriptInterface
        fun getJournalEntriesJson(): String {
            val entries = readJournalEntries()
            val arr = org.json.JSONArray()
            entries.forEach { (word, fav) ->
                val obj = org.json.JSONObject()
                obj.put("word", word)
                obj.put("favorite", fav)
                arr.put(obj)
            }
            return arr.toString()
        }

        /** Günlükten TEK bir kaydı (ilk eşleşeni) siler. */
        @JavascriptInterface
        fun deleteJournalWord(word: String) {
            val entries = readJournalEntries()
            val idx = entries.indexOfFirst { it.first == word }
            if (idx >= 0) entries.removeAt(idx)
            writeJournalEntries(entries)
        }

        /** Günlükteki TÜM kayıtları siler. */
        @JavascriptInterface
        fun clearJournal() {
            writeJournalEntries(emptyList())
        }

        /** Bir kaydın favori durumunu (yıldız) açar/kapatır. */
        @JavascriptInterface
        fun toggleJournalFavorite(word: String) {
            val entries = readJournalEntries()
            val idx = entries.indexOfFirst { it.first == word }
            if (idx >= 0) {
                entries[idx] = entries[idx].first to !entries[idx].second
            }
            writeJournalEntries(entries)
        }
    }
}
