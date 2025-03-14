package com.example.pokedex.list.data

import android.accounts.NetworkErrorException
import com.example.pokedex.common.model.PokeListResponse
import com.example.pokedex.list.data.local.PokeListLocalDataSource
import com.example.pokedex.list.data.remote.PokeListRemoteDataSource
import com.example.pokedex.list.data.remote.PokeListService

class PokeListRepository(
    private val local: PokeListLocalDataSource,
    private val remote: PokeListRemoteDataSource
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

