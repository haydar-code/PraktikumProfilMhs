package com.example.praktikumprofilmhs.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumprofilmhs.data.Tugas
import com.example.praktikumprofilmhs.data.TugasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TugasViewModel(private val repo: TugasRepository) : ViewModel() {

    // UI state: filter status (null = semua) dan kata kunci pencarian
    private val _filterStatus = MutableStateFlow<String?>(null)
    val filterStatus: StateFlow<String?> = _filterStatus

    private val _kataKunci = MutableStateFlow("")
    val kataKunci: StateFlow<String> = _kataKunci

    // Daftar tugas = data Room + filter + pencarian (otomatis ter-update)
    val daftar: StateFlow<List<Tugas>> = combine(
        repo.semua, _filterStatus, _kataKunci
    ) { list, status, kata ->
        list.filter { t ->
            (status == null || t.status == status) &&
                (kata.isBlank() ||
                    t.judul.contains(kata, ignoreCase = true) ||
                    t.mataKuliah.contains(kata, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setFilter(status: String?) { _filterStatus.value = status }
    fun setKataKunci(kata: String) { _kataKunci.value = kata }

    fun ambil(id: Int): Flow<Tugas?> = repo.ambil(id)

    fun tambah(tugas: Tugas) { viewModelScope.launch { repo.tambah(tugas) } }
    fun ubah(tugas: Tugas) { viewModelScope.launch { repo.ubah(tugas) } }
    fun hapus(tugas: Tugas) { viewModelScope.launch { repo.hapus(tugas) } }
    fun ubahStatus(tugas: Tugas, status: String) { ubah(tugas.copy(status = status)) }
}
