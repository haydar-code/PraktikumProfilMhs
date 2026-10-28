package com.example.praktikumprofilmhs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.praktikumprofilmhs.data.PrioritasTugas
import com.example.praktikumprofilmhs.data.StatusTugas
import com.example.praktikumprofilmhs.data.Tugas
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(vm: TugasViewModel, tugasId: Int?, onSelesai: () -> Unit) {
    val flow = remember(tugasId) {
        if (tugasId != null) vm.ambil(tugasId) else flowOf<Tugas?>(null)
    }
    val tugasLama by flow.collectAsState(initial = null)

    var mataKuliah by rememberSaveable { mutableStateOf("") }
    var judul by rememberSaveable { mutableStateOf("") }
    var deskripsi by rememberSaveable { mutableStateOf("") }
    var deadline by rememberSaveable { mutableStateOf("") }
    var prioritas by rememberSaveable { mutableStateOf(PrioritasTugas.SEDANG) }
    var status by rememberSaveable { mutableStateOf(StatusTugas.BELUM) }
    var terisi by rememberSaveable { mutableStateOf(false) }
    var sudahCoba by rememberSaveable { mutableStateOf(false) }

    // Mode edit: isi form sekali dengan data lama dari database
    LaunchedEffect(tugasLama) {
        val t = tugasLama
        if (t != null && !terisi) {
            mataKuliah = t.mataKuliah
            judul = t.judul
            deskripsi = t.deskripsi
            deadline = t.deadline
            prioritas = t.prioritas
            status = t.status
            terisi = true
        }
    }

    val mkValid = mataKuliah.isNotBlank()
    val judulValid = judul.isNotBlank()
    val deadlineValid = TanggalUtil.valid(deadline.trim())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (tugasId == null) "Tambah Tugas" else "Edit Tugas") },
                navigationIcon = { TextButton(onClick = onSelesai) { Text("Batal") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = mataKuliah,
                onValueChange = { mataKuliah = it },
                label = { Text("Mata kuliah") },
                isError = sudahCoba && !mkValid,
                supportingText = { if (sudahCoba && !mkValid) Text("Wajib diisi") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = judul,
                onValueChange = { judul = it },
                label = { Text("Judul tugas") },
                isError = sudahCoba && !judulValid,
                supportingText = { if (sudahCoba && !judulValid) Text("Wajib diisi") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = deskripsi,
                onValueChange = { deskripsi = it },
                label = { Text("Deskripsi") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = deadline,
                onValueChange = { deadline = it },
                label = { Text("Deadline (yyyy-MM-dd)") },
                placeholder = { Text("2026-11-15") },
                isError = sudahCoba && !deadlineValid,
                supportingText = {
                    if (sudahCoba && !deadlineValid) Text("Format harus yyyy-MM-dd, contoh 2026-11-15")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Prioritas")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PrioritasTugas.semua.forEach { p ->
                    FilterChip(
                        selected = prioritas == p,
                        onClick = { prioritas = p },
                        label = { Text(p) }
                    )
                }
            }

            Text("Status")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusTugas.semua.forEach { s ->
                    FilterChip(
                        selected = status == s,
                        onClick = { status = s },
                        label = { Text(s) }
                    )
                }
            }

            Button(
                onClick = {
                    sudahCoba = true
                    if (mkValid && judulValid && deadlineValid) {
                        val t = Tugas(
                            id = tugasId ?: 0,
                            mataKuliah = mataKuliah.trim(),
                            judul = judul.trim(),
                            deskripsi = deskripsi.trim(),
                            deadline = deadline.trim(),
                            prioritas = prioritas,
                            status = status
                        )
                        if (tugasId == null) vm.tambah(t) else vm.ubah(t)
                        onSelesai()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }
}
