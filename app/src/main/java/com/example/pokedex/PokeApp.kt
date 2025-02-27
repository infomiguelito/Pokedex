package com.example.pokedex

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun PokeApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "pokeList") {
        composable(route = "pokeList") {
            PokeListScreen(navController)
        }
        composable(
            route = "pokeDetail" + "/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val pokeId =  requireNotNull( backStackEntry.arguments?.getString("itemId"))
            PokeDetailScreen(pokeId)
        }
    }
}