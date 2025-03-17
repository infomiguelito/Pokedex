package com.example.pokedex.list.data.remote

import android.accounts.NetworkErrorException
import com.example.pokedex.common.data.model.Poke

class PokeListRemoteDataSource(
    private val pokeListService: PokeListService
) {
    suspend fun getPokeList(): Result<List<Poke>?> {
        return try {
            val response = pokeListService.getPokemonList()
            if (response.isSuccessful) {
                val pokemons = response.body()?.results?.map { pokeItem ->
                    Poke(
                        name = pokeItem.name,
                        url = pokeItem.url,
                        id = pokeItem.id,
                        image = pokeItem.image
                    )
                }
                Result.success(pokemons)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}