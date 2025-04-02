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
            Log.d("PokeListLocalDataSource", "Retrieved ${entities.size} Pokemon from database")
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
        Log.d("PokeListLocalDataSource", "Attempting to save ${pokemons.size} Pokemon to database")
        
        // Verifica se já existem Pokémon no banco
        val currentCount = dao.getPokemonCount()
        Log.d("PokeListLocalDataSource", "Current Pokemon count in database: $currentCount")
        
        if (currentCount < pokemons.size) {
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
            Log.d("PokeListLocalDataSource", "Successfully saved ${entities.size} Pokemon to database")
        } else {
            Log.d("PokeListLocalDataSource", "Skipping save as database already has more Pokemon")
        }
    }
}