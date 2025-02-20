package com.example.pokedex

import com.google.gson.annotations.SerializedName

data class PokeResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val abilities: List<AbilityWrapper>
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String
)

data class AbilityWrapper(
    val ability: Ability
)

data class Ability(
    val name: String
)

fun PokeResponse.toDto() : PokeDto{
    return PokeDto(
        id = this.id,
        name = this.name,
        image = this.sprites.frontDefault,
        summary = "Resumo do Pokémon ${this.name}"
    )
}