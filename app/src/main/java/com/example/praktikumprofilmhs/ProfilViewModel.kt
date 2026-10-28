
package com.example.praktikumprofilmhs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfilViewModel : ViewModel() {
    var nama by mutableStateOf("Haydar Ramadhan Abdala")
        private set
    var prodi by mutableStateOf("Informatika")
        private set

    fun ubahNama(input: String) {
        nama = input
    }

}