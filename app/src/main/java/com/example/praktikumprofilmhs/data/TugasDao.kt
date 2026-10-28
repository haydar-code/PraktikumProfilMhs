package com.example.praktikumprofilmhs.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TugasDao {
    @Query("SELECT * FROM tugas ORDER BY deadline ASC, id DESC")
    fun semua(): Flow<List<Tugas>>

    @Query("SELECT * FROM tugas WHERE id = :id")
    fun ambil(id: Int): Flow<Tugas?>

    @Insert
    suspend fun tambah(tugas: Tugas)

    @Update
    suspend fun ubah(tugas: Tugas)

    @Delete
    suspend fun hapus(tugas: Tugas)
}
