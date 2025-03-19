package com.example.pokedex.detail.data

import com.example.pokedex.common.data.remote.model.PokeDto
import com.example.pokedex.detail.data.local.PokeDetailLocalDataSource
import com.example.pokedex.detail.data.remote.PokeDetailRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlin.Result

class PokeDetailRepository(
    private val localDataSource: PokeDetailLocalDataSource,
    private val remoteDataSource: PokeDetailRemoteDataSource
) {
    suspend fun getPokemonDetails(id: String): Result<PokeDto?> {
        return try {
            val localPokemon = localDataSource.getPokemonById(id).first()
            if (localPokemon != null) {
                updateFromRemote(id)
                Result.success<PokeDto?>(localPokemon)
            } else {
                remoteDataSource.getPokemonDetails(id).onSuccess { pokemon ->
                    pokemon?.let { localDataSource.savePokemon(it) }
                    Result.success(pokemon)
                }.onFailure { error ->
                    Result.failure<PokeDto?>(error)
                }
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private suspend fun updateFromRemote(id: String) {
        try {
            remoteDataSource.getPokemonDetails(id).onSuccess { pokemon ->
                pokemon?.let { localDataSource.savePokemon(it) }
            }
        } catch (e: Exception) {

        }
    }
} 