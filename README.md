## 🚀 Cara Menjalankan Project (Setup Guide)

Ikuti langkah-langkah berikut untuk menghubungkan aplikasi ke Firebase milik Anda:

### Langkah 1: Cek Package Name
1. Buka project di **Android Studio**.
2. Buka file `app/build.gradle.kts` (atau `build.gradle`).
3. Cari bagian `defaultConfig` dan catat **applicationId**.
   > Contoh: `com.example.modernreminder`

### Langkah 2: Buat Project di Firebase Console
1. Buka [Firebase Console](https://console.firebase.google.com/).
2. Klik **Add Project** (beri nama bebas, misal: `MyTodoList`).
3. Matikan *Google Analytics* (opsional, agar setup lebih cepat), lalu klik **Create Project**.

### Langkah 3: Daftarkan Aplikasi Android
1. Buka Project Settings.
2. Di bagian general scroll ke bawah, kan ada your apps, terus add app.
3. **Android package name**: Tempelkan `applicationId` yang Anda catat di Langkah 1.
4. Klik **Register app**.

### Langkah 4: Tambahkan `google-services.json` (PENTING!)
1. Download file **`google-services.json`** yang diberikan oleh Firebase.
2. Pindahkan file tersebut ke dalam folder **`app`** di project Android Studio Anda.
   > Lokasi: `ProjectName/app/google-services.json`
3. Klik **Next** di console Firebase sampai selesai.

### Langkah 5: Setup Realtime Database
1. Di menu kiri Firebase Console, pilih **Build** > **Realtime Database**.
2. Klik **Create Database**.
3. Pilih lokasi server (pilih Singapore/US, bebas).
4. Pada bagian **Security Rules**, pilih **Test Mode** agar aplikasi bisa langsung Baca/Tulis tanpa login.

Pastikan Rules terlihat seperti ini (atau memiliki batas waktu):
```json
{
  "rules": {
    ".read": "now < 1767027600000",  
    ".write": "now < 1767027600000" 
  }
}
```

### Langkah 6: Jalankan Aplikasi

1.  Kembali ke Android Studio.
2.  Lakukan **Sync Project with Gradle Files** (Ikon Gajah di kanan atas).
3.  Jika sync sukses, klik tombol **Run 'app'** (Shift+F10).
4.  Aplikasi siap digunakan\! Coba tambahkan tugas baru untuk mengetes koneksi database.

-----

## 🛠️ Tech Stack & Fitur

* **Language:** Kotlin
* **UI:** XML (Material Design 3)
* **Architecture:** MVVM Pattern (Simple)
* **Database:** Firebase Realtime Database
* **Key Libraries:**
    * ViewBinding
    * Firebase BOM
    * RecyclerView & ListAdapter

## 🐞 Troubleshooting

**Q: Aplikasi Crash saat dibuka?**
A: Pastikan file `google-services.json` sudah ada di dalam folder `app/`. Coba lakukan **Build \> Clean Project** lalu **Rebuild Project**.

**Q: Data tidak muncul / Kosong?**
A: Karena Anda menggunakan database baru, datanya pasti kosong. Coba input data baru lewat tombol (+) di aplikasi.

**Q: Error "Permission Denied"?**
A: Cek kembali **Rules** di Realtime Database Firebase Console. Pastikan `.read` dan `.write` bernilai `true` atau tanggalnya valid.

