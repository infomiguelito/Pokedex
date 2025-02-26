package com.example.pokedex.utils

import androidx.compose.ui.graphics.Color

fun  getPokemonColor(type: String): Color {
    return when (type.lowercase()) {
        "fire" -> Color(0xFFFFA726)  // Laranja
        "water" -> Color(0xFF42A5F5) // Azul
        "grass" -> Color(0xFF66BB6A) // Verde
        "electric" -> Color(0xFFFFEB3B) // Amarelo
        "ice" -> Color(0xFF81D4FA)  // Azul claro
        "fighting" -> Color(0xFFD32F2F) // Vermelho escuro
        "poison" -> Color(0xFF9C27B0)  // Roxo
        "ground" -> Color(0xFF8D6E63)  // Marrom
        "flying" -> Color(0xFF81C784)  // Verde claro
        "psychic" -> Color(0xFFEC407A) // Rosa
        "bug" -> Color(0xFF8BC34A) // Verde mais escuro
        "rock" -> Color(0xFF795548) // Marrom escuro
        "ghost" -> Color(0xFF7E57C2) // Roxo escuro
        "dragon" -> Color(0xFFAB47BC) // Roxo brilhante
        "dark" -> Color(0xFF424242) // Cinza escuro
        "steel" -> Color(0xFFB0BEC5) // Cinza metálico
        "fairy" -> Color(0xFFF48FB1) // Rosa claro
        else -> Color.Gray // Cor padrão
    }
}