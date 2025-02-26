package com.example.pokedex

import com.google.gson.annotations.SerializedName

data class PokeDto(
    val name: String,
    val url: String?

){
    val frontDefault : String
        get() = url?.split("/")?.dropLast(1)?.lastOrNull() ?: "0"

    val frontFullDefault: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$frontDefault.png"

}
