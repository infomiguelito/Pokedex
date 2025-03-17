package com.example.pokedex.list.data.local

import com.example.pokedex.common.data.local.PokeDao
import com.example.pokedex.common.data.model.Poke
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
}