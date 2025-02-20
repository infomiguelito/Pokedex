package com.example.pokedex

data class PokeResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val abilities: List<AbilityWrapper>
)

data class Sprites(
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