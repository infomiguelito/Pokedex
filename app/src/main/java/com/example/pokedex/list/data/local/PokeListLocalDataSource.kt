package com.example.pokedex.list.data.local

import android.util.Log
import com.example.pokedex.common.data.local.PokeDao
import com.example.pokedex.common.data.local.PokeEntity
import com.example.pokedex.common.data.model.Poke
import com.example.pokedex.common.data.remote.model.PokeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokeListLocalDataSource(
    private val dao: PokeDao
) {
    fun getPokemon(): Flow<List<Poke>> {
        return dao.getAllPokemons().map { entities ->
            entities.map { entity ->
                Poke(
                    name = entity.name,
                    url = entity.url,
                    id = entity.url?.split("/")?.dropLast(1)?.last() ?: "",
                    image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${entity.url?.split("/")?.dropLast(1)?.last()}.png"
                )
            }
        }
    }

    suspend fun savePokemonList(pokemons: List<Poke>) {
        Log.d("PokeListLocalDataSource", "Saving ${pokemons.size} Pokemon to database")
        val entities = pokemons.map { pokemon ->
            PokeEntity(
                name = pokemon.name,
                url = pokemon.url,
                height = 0,
                weight = 0,
                types = emptyList(),
                stats = emptyList(),
                sprites = PokeDto.Sprites(front_default = pokemon.image)
            )
        }
        dao.insertPokemonList(entities)
    }
}