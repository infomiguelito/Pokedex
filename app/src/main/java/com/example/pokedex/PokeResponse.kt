package com.example.pokedex

import com.google.gson.annotations.SerializedName

data class PokeResponse(
  val results: List<PokeDto>
)
