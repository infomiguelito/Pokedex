package com.example.pokedex

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.detail.presentation.PokeDetailViewModel
import com.example.pokedex.detail.presentation.ui.PokeDetailScreen
import com.example.pokedex.list.presentation.PokeListViewModel
import com.example.pokedex.list.presentation.ui.PokeListScreen

@Composable
fun PokeApp(
    listViewModel: PokeListViewModel,
    detailViewModel: PokeDetailViewModel
) {
    val navController = rememberNavController()
    NavHost(navController, "pokeList") {
        composable(route = "pokeList") {
            PokeListScreen(navController, listViewModel)
        }
        composable(
            route = "pokeDetail/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val pokeId = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            PokeDetailScreen(
                pokemonId = pokeId,
                viewModel = detailViewModel,
                navController = navController
            )
        }
    }
}
