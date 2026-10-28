package com.example.praktikumprofilmhs.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

object Rute {
    const val DAFTAR = "daftar"
    const val TAMBAH = "form"
    const val EDIT = "form/{id}"
    const val DETAIL = "detail/{id}"
    fun edit(id: Int) = "form/$id"
    fun detail(id: Int) = "detail/$id"
}

@Composable
fun AppNavigation(vm: TugasViewModel) {
    val nav = rememberNavController()
    val argId = listOf(navArgument("id") { type = NavType.IntType })

    NavHost(navController = nav, startDestination = Rute.DAFTAR) {
        composable(Rute.DAFTAR) {
            DaftarScreen(
                vm = vm,
                onTambah = { nav.navigate(Rute.TAMBAH) },
                onDetail = { id -> nav.navigate(Rute.detail(id)) }
            )
        }
        composable(Rute.TAMBAH) {
            FormScreen(vm = vm, tugasId = null, onSelesai = { nav.popBackStack() })
        }
        composable(Rute.EDIT, arguments = argId) { entry ->
            FormScreen(
                vm = vm,
                tugasId = entry.arguments?.getInt("id"),
                onSelesai = { nav.popBackStack() }
            )
        }
        composable(Rute.DETAIL, arguments = argId) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            DetailScreen(
                vm = vm,
                id = id,
                onEdit = { nav.navigate(Rute.edit(id)) },
                onKembali = { nav.popBackStack() }
            )
        }
    }
}
