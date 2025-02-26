package com.example.pokedex


data class PokeResponse(
  val results: List<PokeDto>
)

data class PokemonDetail(
  val name: String,
  val height: Int,
  val weight: Int,
  val sprites: Sprite,
  val types: List<Type>
)


data class Sprite(val frontDefault: String)

data class Type(val type: TypeName)

data class TypeName(val name: String)