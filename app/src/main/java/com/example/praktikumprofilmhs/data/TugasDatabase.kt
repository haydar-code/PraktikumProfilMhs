package com.example.praktikumprofilmhs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tugas::class], version = 1, exportSchema = false)
abstract class TugasDatabase : RoomDatabase() {
    abstract fun dao(): TugasDao

    companion object {
        @Volatile
        private var INSTANCE: TugasDatabase? = null

        fun getInstance(context: Context): TugasDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TugasDatabase::class.java,
                    "tugas-mahasiswa.db"
                ).build().also { INSTANCE = it }
            }
    }
}
