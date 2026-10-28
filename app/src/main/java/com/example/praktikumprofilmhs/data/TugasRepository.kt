package com.example.praktikumprofilmhs.data

import kotlinx.coroutines.flow.Flow

class TugasRepository(private val dao: TugasDao) {
    val semua: Flow<List<Tugas>> = dao.semua()

    fun ambil(id: Int): Flow<Tugas?> = dao.ambil(id)

    suspend fun tambah(tugas: Tugas) = dao.tambah(tugas)
    suspend fun ubah(tugas: Tugas) = dao.ubah(tugas)
    suspend fun hapus(tugas: Tugas) = dao.hapus(tugas)
}
