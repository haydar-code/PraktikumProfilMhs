package com.example.praktikumprofilmhs.ui

import com.example.praktikumprofilmhs.data.StatusTugas
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TanggalUtil {
    private fun format() = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply { isLenient = false }

    fun valid(teks: String): Boolean {
        if (!Regex("\\d{4}-\\d{2}-\\d{2}").matches(teks)) return false
        return try { format().parse(teks) != null } catch (e: Exception) { false }
    }

    fun terlambat(deadline: String, status: String): Boolean {
        if (status == StatusTugas.SELESAI) return false
        return try {
            val f = format()
            val d = f.parse(deadline) ?: return false
            val hariIni = f.parse(f.format(Date())) ?: return false
            d.before(hariIni)
        } catch (e: Exception) {
            false
        }
    }
}
