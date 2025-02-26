package com.example.pokedex

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun PokeApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "pokeList") {
        composable(route = "pokeList"){
            PokeListScreen(navController)
        }
        composable(route = "pokeDetail"){
            PokeDetailScreen()
        }
    }
}