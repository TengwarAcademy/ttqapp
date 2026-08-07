# Türkçe Tengwar Klavye (Android)

## Sadece telefonla, bilgisayarsız APK almak

Bu ZIP'in içinde `.github/workflows` klasörü **yok** — çünkü GitHub'ın bunu
otomatik derleyebilmesi için o dosyanın depo kökünde ayrıca, kendi başına
durması gerekiyor. Akış şöyle: tek bir dosyayı kopyala-yapıştır ile oluşturuyorsun,
sonra bu ZIP'in tamamını **tek dosya olarak** yüklüyorsun — hiçbir klasör
sürüklemene gerek yok, tamamen telefon tarayıcısından yapılabilir.

1. github.com'da ücretsiz bir hesap aç (yoksa).
2. Sağ üstteki **+** > **New repository** ile yeni, **Public** bir depo oluştur
   (ad önemli değil, ör. `tengwar-klavye`).
3. Depo sayfasında **Add file > Create new file** de. Dosya adı kutusuna
   tam olarak şunu yaz: `.github/workflows/build-apk.yml`
   (GitHub, içindeki `/` işaretlerinden klasörleri kendisi oluşturur).
4. Aşağıdaki metnin tamamını kopyala, dosyanın içine yapıştır, **Commit
   changes** de:

   ```yaml
   name: Tengwar Klavye APK Derle

   on:
     push:
     workflow_dispatch:

   jobs:
     build:
       runs-on: ubuntu-latest
       steps:
         - name: Kodu al
           uses: actions/checkout@v4

         - name: Proje zip'ini aç
           run: unzip -o *.zip -d .

         - name: Java 17 kur
           uses: actions/setup-java@v4
           with:
             distribution: 'temurin'
             java-version: '17'

         - name: Android SDK kur
           uses: android-actions/setup-android@v3

         - name: Gradle kur
           uses: gradle/actions/setup-gradle@v3

         - name: Debug APK derle
           working-directory: TengwarKlavye
           run: gradle assembleDebug

         - name: APK'yı yükle (Artifacts)
           uses: actions/upload-artifact@v4
           with:
             name: TengwarKlavye-apk
             path: TengwarKlavye/app/build/outputs/apk/debug/app-debug.apk
   ```

5. Depo ana sayfasına dön, **Add file > Upload files** de. Telefonuna
   indirdiğin **bu ZIP dosyasının kendisini** (çıkarmadan, olduğu gibi)
   seç ve yükle. Tek dosya seçimi olduğu için telefon tarayıcısından da
   sorunsuz çalışır. **Commit changes** de.
6. Yükleme biter bitmez derleme otomatik başlar. Üstteki **Actions**
   sekmesine git, çalışan işe tıkla, ~3-5 dakika bitmesini bekle (yeşil
   onay işareti).
7. Aynı sayfanın altındaki **Artifacts** bölümünden **TengwarKlavye-apk**
   dosyasını indir (bir .zip, içinde `app-debug.apk` var).
8. Telefonundaki herhangi bir dosya yöneticisi/zip uygulamasıyla bu zip'i
   aç, `app-debug.apk`'ya dokun. Telefon "bilinmeyen kaynaklardan yükleme"
   izni isteyecek — tarayıcı ya da Dosyalar uygulaması için bunu bir
   kerelik onayla, kurulum tamamlanır.
9. Açılan uygulamada **"Klavyeyi Etkinleştir"** → Ayarlar'dan "Türkçe
   Tengwar Klavye"yi aç, sonra **"Klavyeyi Seç"** (ya da herhangi bir metin
   kutusunda klavye simgesine basılı tutup) ile seç. Artık her uygulamada
   kullanabilirsin.

> Not: Bu derlemeyi ben burada tetikleyip senin yerine çalıştıramıyorum —
> GitHub Actions'ın çalışması için deponun senin hesabında olması gerekiyor.
> Ama yukarıdaki iş akışı gerçek ve test edilebilir; Actions sekmesinde
> kırmızı bir hata görürsen ekran görüntüsünü/metnini bana gönder, birlikte
> çözelim.

---

## Alternatif: Android Studio ile (bilgisayarın varsa, daha fazla kontrol istersen)

Bu, `TTQ-Klavye.txt` referans çalışmandaki dört modlu (Standart / Shift / AltGr /
Shift+AltGr) tuş haritasını temel alan, **gerçek bir Android sistem klavyesi**
(Input Method Editor / IME) projesidir. Kurulduktan ve etkinleştirildikten sonra
WhatsApp, Notlar, tarayıcı, Word gibi **herhangi bir uygulamada** doğrudan
Tengwar yazabilirsin.

> ⚠️ Bu projeyi bu sohbet ortamında (Android SDK'sız bir Linux kutusu) derleyip
> çalıştıramadım — sadece kaynak kodunu yazabildim. Aşağıdaki adımlarla kendi
> bilgisayarında Android Studio'da açıp derlemen gerekiyor. Kod standart, iyi
> bilinen bir IME deseni izliyor; olası küçük derleme hatalarını (ör. Gradle/AGP
> sürüm uyumsuzluğu) Android Studio kolayca gösterecektir.

## Nasıl derlenir / kurulur

1. **Android Studio**'yu aç (yoksa güncel sürümünü indir).
2. `File > Open` ile bu `TengwarKlavye` klasörünü seç. Studio, Gradle wrapper
   dosyaları eksikse otomatik olarak oluşturmayı teklif edecektir — kabul et.
3. İlk senkronizasyon sırasında Android Studio gerekli SDK bileşenlerini
   (compileSdk 34, minSdk 24) otomatik indirir.
4. Telefonunu USB ile bağla (Geliştirici Seçenekleri > USB hata ayıklama açık)
   veya bir emülatör başlat, sonra **Run ▶** düğmesine bas.
5. Uygulama telefona kurulunca açılan ekranda:
   - **"Klavyeyi Etkinleştir"** düğmesine bas → Ayarlar > Diller ve Girdi'den
     "Türkçe Tengwar Klavye"yi aç.
   - **"Klavyeyi Seç"** düğmesine bas (veya herhangi bir metin kutusunda klavye
     simgesine basılı tut) → "Türkçe Tengwar Klavye"yi seç.
6. Artık herhangi bir uygulamada bu klavyeyle Tengwar yazabilirsin.

## Proje yapısı

```
TengwarKlavye/
  app/src/main/
    java/com/tengwar/klavye/
      KeymapData.kt     — TTQ-Klavye.txt'teki tuş haritasının Kotlin karşılığı
                           (otomatik üretildi, bkz. shared/build_keymap.py)
      TengwarIME.kt      — Asıl klavye servisi: tuşları çizer, moda göre
                           tengwa glifini InputConnection.commitText ile yazar
      SetupActivity.kt   — Kurulum ekranı (Ayarlar'a yönlendirme)
    assets/fonts/TengwarAnnatar.ttf — tuşlardaki glifleri çizmek için kullanılan font
    res/xml/method.xml   — IME alt tip tanımı
```

## Tuş haritasını güncellemek istersen

`shared/keymap.json` tek doğruluk kaynağıdır (bu ZIP'te `shared/` klasörü olarak
da veriliyor). Onu düzenleyip `shared/gen_kotlin.py` betiğini tekrar çalıştırman
yeter; `KeymapData.kt` yeniden üretilir. Aynı `keymap.json`, Windows tarafındaki
kayan panel ve klavye düzeni için de kaynak olarak kullanılacak — böylece üç
platform da aynı harita üzerinden senkron kalır.

## Bilinen sınırlamalar / sonraki adımlar

- Şu an her tuşa dokununca tek bir karakter yazılıyor (web örneğindeki "tıkla,
  panoya kopyala" davranışı yerine gerçek klavye girişi).
- Uzun basma / kaydırarak yazma (swipe) yok; istersen sonraki adımda eklenebilir.
- Font asset olarak gömülü; farklı bir Tengwar fontu istersen `assets/fonts/`
  içindeki dosyayı değiştirip `TengwarIME.kt` içindeki dosya adını güncelle.
