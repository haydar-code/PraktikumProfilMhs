package com.example.praktikumprofilmhs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikumprofilmhs.data.TugasDatabase
import com.example.praktikumprofilmhs.data.TugasRepository
import com.example.praktikumprofilmhs.ui.AppNavigation
import com.example.praktikumprofilmhs.ui.TugasViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = TugasDatabase.getInstance(applicationContext)
        val repo = TugasRepository(db.dao())
        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TugasViewModel(repo) as T
            }
        }

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val vm: TugasViewModel = viewModel(factory = factory)
                    AppNavigation(vm)
                }
            }
        }
    }
}
