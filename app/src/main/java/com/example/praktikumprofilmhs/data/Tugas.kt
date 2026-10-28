package com.example.praktikumprofilmhs.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tugas")
data class Tugas(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mataKuliah: String,
    val judul: String,
    val deskripsi: String,
    val deadline: String,   // format yyyy-MM-dd
    val prioritas: String,
    val status: String
)

object StatusTugas {
    const val BELUM = "Belum"
    const val PROSES = "Proses"
    const val SELESAI = "Selesai"
    val semua = listOf(BELUM, PROSES, SELESAI)
}

object PrioritasTugas {
    const val RENDAH = "Rendah"
    const val SEDANG = "Sedang"
    const val TINGGI = "Tinggi"
    val semua = listOf(RENDAH, SEDANG, TINGGI)
}
