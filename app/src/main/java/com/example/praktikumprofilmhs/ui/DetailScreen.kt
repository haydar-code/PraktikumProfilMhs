package com.example.praktikumprofilmhs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.praktikumprofilmhs.data.StatusTugas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    vm: TugasViewModel,
    id: Int,
    onEdit: () -> Unit,
    onKembali: () -> Unit
) {
    val flow = remember(id) { vm.ambil(id) }
    val tugas by flow.collectAsState(initial = null)
    var tampilHapus by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas") },
                navigationIcon = { TextButton(onClick = onKembali) { Text("Kembali") } }
            )
        }
    ) { padding ->
        val t = tugas
        if (t == null) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Memuat...")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(t.judul, style = MaterialTheme.typography.headlineSmall)
                Baris("Mata kuliah", t.mataKuliah)
                Baris("Deskripsi", t.deskripsi.ifBlank { "-" })
                Baris(
                    "Deadline",
                    t.deadline + if (TanggalUtil.terlambat(t.deadline, t.status)) " (terlambat)" else ""
                )
                Baris("Prioritas", t.prioritas)

                Text("Status", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusTugas.semua.forEach { s ->
                        FilterChip(
                            selected = t.status == s,
                            onClick = { vm.ubahStatus(t, s) },
                            label = { Text(s) }
                        )
                    }
                }

                Button(onClick = onEdit, modifier = Modifier.fillMaxWidth()) { Text("Edit") }
                OutlinedButton(
                    onClick = { tampilHapus = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Hapus") }
            }

            if (tampilHapus) {
                AlertDialog(
                    onDismissRequest = { tampilHapus = false },
                    title = { Text("Hapus tugas?") },
                    text = { Text("Tugas \"${t.judul}\" akan dihapus permanen.") },
                    confirmButton = {
                        TextButton(onClick = {
                            tampilHapus = false
                            vm.hapus(t)
                            onKembali()
                        }) { Text("Hapus") }
                    },
                    dismissButton = {
                        TextButton(onClick = { tampilHapus = false }) { Text("Batal") }
                    }
                )
            }
        }
    }
}

@Composable
private fun Baris(label: String, nilai: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(nilai, style = MaterialTheme.typography.bodyLarge)
    }
}
