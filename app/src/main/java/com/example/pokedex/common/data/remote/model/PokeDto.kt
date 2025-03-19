package com.example.pokedex.common.data.remote.model


 data class PokeDto(
     val name: String,
     val url: String?,
     val height: Int,
     val weight: Int,
     val types: List<TypeSlot>,
     val stats: List<StatSlot>,
     val sprites: Sprites

)  {




     data class TypeSlot(val type: PokemonType)
    data class PokemonType(val name: String)

    data class StatSlot(val base_stat: Int, val stat: Stat)
    data class Stat(val name: String)

    data class Sprites(val front_default: String)
}


