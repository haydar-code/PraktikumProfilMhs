package com.example.praktikumprofilmhs.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.praktikumprofilmhs.data.PrioritasTugas
import com.example.praktikumprofilmhs.data.StatusTugas
import com.example.praktikumprofilmhs.data.Tugas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarScreen(
    vm: TugasViewModel,
    onTambah: () -> Unit,
    onDetail: (Int) -> Unit
) {
    val daftar by vm.daftar.collectAsState()
    val filter by vm.filterStatus.collectAsState()
    val kata by vm.kataKunci.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tugas Mahasiswa") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onTambah) { Text("+ Tambah Tugas") }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = kata,
                onValueChange = vm::setKataKunci,
                label = { Text("Cari judul / mata kuliah") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = filter == null,
                    onClick = { vm.setFilter(null) },
                    label = { Text("Semua") }
                )
                StatusTugas.semua.forEach { s ->
                    FilterChip(
                        selected = filter == s,
                        onClick = { vm.setFilter(s) },
                        label = { Text(s) }
                    )
                }
            }

            if (daftar.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada tugas. Tekan \"+ Tambah Tugas\".")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 88.dp)
                ) {
                    items(daftar, key = { it.id }) { t ->
                        KartuTugas(t) { onDetail(t.id) }
                    }
                }
            }
        }
    }
}

@Composable
private fun KartuTugas(t: Tugas, onClick: () -> Unit) {
    val terlambat = TanggalUtil.terlambat(t.deadline, t.status)
    val merah = MaterialTheme.colorScheme.error

    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(t.judul, style = MaterialTheme.typography.titleMedium)
            Text(t.mataKuliah, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Deadline: ${t.deadline}" + if (terlambat) " (terlambat)" else "",
                color = if (terlambat) merah else Color.Unspecified
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Prioritas: ${t.prioritas}",
                    color = if (t.prioritas == PrioritasTugas.TINGGI) merah else Color.Unspecified
                )
                Text("Status: ${t.status}")
            }
        }
    }
}
