package com.example.pokedex.list.data.remote

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.pokedex.common.data.model.Poke

class PokeListRemoteDataSource(
    private val pokeListService: PokeListService
) {
    suspend fun getPokeList(): Result<List<Poke>?> {
        return try {
            val response = pokeListService.getPokemonList()
            Log.d("PokeListRemoteDataSource", "Response: ${response.isSuccessful}, Code: ${response.code()}")
            
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("PokeListRemoteDataSource", "Body: $body")
                
                val pokemons = body?.results?.map { pokeItem ->
                    Poke(
                        name = pokeItem.name,
                        url = pokeItem.url,
                        id = pokeItem.id,
                        image = pokeItem.image
                    )
                }
                Result.success(pokemons)
            } else {
                Log.e("PokeListRemoteDataSource", "Error: ${response.code()} - ${response.message()}")
                Result.failure(NetworkErrorException("Error ${response.code()}: ${response.message()}"))
            }
        } catch (ex: Exception) {
            Log.e("PokeListRemoteDataSource", "Exception: ${ex.message}", ex)
            Result.failure(ex)
        }
    }
}