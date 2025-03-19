package com.example.pokedex.detail.data.remote

import android.accounts.NetworkErrorException
import com.example.pokedex.common.data.remote.model.PokeDto
import com.example.pokedex.detail.data.remote.PokeDetailService

class PokeDetailRemoteDataSource(
    private val pokeDetailService: PokeDetailService
) {
    suspend fun getPokemonDetails(id: String): Result<PokeDto?> {
        return try {
            val response = pokeDetailService.getPokemonDetails(id)
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