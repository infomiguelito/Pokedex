package com.example.pokedex.common.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedex.common.model.PokeDto.Sprites
import com.example.pokedex.common.model.PokeDto.StatSlot
import com.example.pokedex.common.model.PokeDto.TypeSlot

@Entity
data class PokeEntity(
    @PrimaryKey
    val name: String,
    val url: String?,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val stats: List<StatSlot>,
    val sprites: Sprites
)
