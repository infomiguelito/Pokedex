package com.example.pokedex.detail.data.local

import com.example.pokedex.common.data.local.PokeDao
import com.example.pokedex.common.data.local.PokeEntity
import com.example.pokedex.common.data.remote.model.PokeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokeDetailLocalDataSource(
    private val dao: PokeDao
) {
    fun getPokemonById(id: String): Flow<PokeDto?> {
        return dao.getPokemonById(id).map { entity ->
            entity?.let {
                PokeDto(
                    name = it.name,
                    url = it.url,
                    height = it.height,
                    weight = it.weight,
                    types = it.types,
                    stats = it.stats,
                    sprites = it.sprites
                )
            }
        }
    }

    suspend fun savePokemon(pokemon: PokeDto) {
        val entity = PokeEntity(
            name = pokemon.name,
            url = pokemon.url,
            height = pokemon.height,
            weight = pokemon.weight,
            types = pokemon.types,
            stats = pokemon.stats,
            sprites = pokemon.sprites
        )
        dao.insertPokemon(entity)
    }
} 