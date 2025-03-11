package com.example.pokedex.common.model

data class PokeListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokeListItem>
)

data class PokeListItem(
    val name: String,
    val url: String
) {
    val id: String
        get() = url.split("/").dropLast(1).last()

    val image: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
} 