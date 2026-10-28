# PraktikumProfilMhs - Aplikasi Manajemen Tugas Mahasiswa (Offline)

Aplikasi Android untuk mencatat, memantau, dan memperbarui tugas kuliah **tanpa koneksi internet**. Semua data disimpan secara lokal menggunakan Room Database, sehingga tidak hilang saat aplikasi ditutup.

Dibuat sebagai Studi Kasus Praktikum 1 - Offline Mobile Application, mata kuliah Pemrograman Aplikasi Bergerak, Program Studi Informatika, Universitas Mulia.

**Nama:** (Haydar Ramadhan Abdala)
**NIM:** (2411042)
**Kelas:** IFB5A

## Fitur

- Menampilkan daftar tugas dari database lokal
- Menambahkan tugas baru melalui form input
- Melihat detail tugas secara lengkap
- Mengubah data tugas yang sudah tersimpan
- Menghapus tugas
- Mengubah status tugas (belum, proses, selesai)
- Filter daftar tugas berdasarkan status
- Pencarian tugas berdasarkan mata kuliah atau judul
- Berjalan sepenuhnya offline

## Model Data

Setiap tugas memiliki field berikut:

| Field | Keterangan |
|---|---|
| id | Pembeda setiap tugas |
| mataKuliah | Nama mata kuliah |
| judul | Judul tugas |
| deskripsi | Penjelasan tugas |
| deadline | Batas pengumpulan |
| prioritas | Tingkat kepentingan tugas |
| status | Progres pengerjaan |

## Arsitektur

Aplikasi memisahkan tanggung jawab tiap lapisan:

```
Compose UI -> ViewModel -> Repository -> Room DAO -> Database
```

- **UI (Jetpack Compose):** menampilkan data dan mengirim event pengguna. UI tidak mengakses database secara langsung.
- **ViewModel (`TugasViewModel`):** menyimpan UI state, termasuk filter status dan kata kunci pencarian.
- **Repository (`TugasRepository`):** perantara antara ViewModel dan sumber data.
- **Room (`TugasDatabase`, DAO):** penyimpanan lokal berbasis SQLite.
- **Navigation (`AppNavigation`):** mengatur perpindahan antar layar.

## Struktur Project

```
app/src/main/java/com/example/praktikumprofilmhs/
├── MainActivity.kt
├── data/
│   ├── TugasDatabase.kt
│   └── TugasRepository.kt
└── ui/
    ├── AppNavigation.kt
    ├── DaftarScreen.kt
    ├── DetailScreen.kt
    ├── FormScreen.kt
    ├── TanggalUtil.kt
    └── TugasViewModel.kt
```

## Teknologi

- Kotlin
- Jetpack Compose
- ViewModel
- Navigation Compose
- Room Database

## Cara Menjalankan

1. Pastikan Android Studio sudah terpasang.
2. Clone repository ini:
   ```
   git clone https://github.com/haydar-code/PraktikumProfilMhs.git
   ```
3. Buka folder project di Android Studio, lalu tunggu proses Gradle sync selesai.
4. Siapkan emulator (contoh: Medium Phone) atau hubungkan perangkat Android.
5. Klik tombol **Run** (tombol segitiga hijau) untuk menjalankan aplikasi.

## Alur Aplikasi

```
Daftar Tugas -> Tambah Tugas -> Simpan -> Daftar Tugas
Daftar Tugas -> Detail Tugas -> Edit / Hapus -> Daftar Tugas
```

## Screenshot

(Tambahkan screenshot layar daftar, tambah, detail, dan edit di sini.)
