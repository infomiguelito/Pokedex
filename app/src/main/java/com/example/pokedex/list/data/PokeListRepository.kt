package com.example.pokedex.list.data

import android.accounts.NetworkErrorException
import com.example.pokedex.common.model.PokeListResponse

class PokeListRepository(
    private val pokeListService: PokeListService
) {
    suspend fun getPokeList(): Result<PokeListResponse?> {
        return try {
            val response = pokeListService.getPokemonList()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}

