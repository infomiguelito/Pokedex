package com.example.pokedex.list.data

import android.accounts.NetworkErrorException
import com.example.pokedex.common.data.model.Poke
import com.example.pokedex.list.data.local.PokeListLocalDataSource
import com.example.pokedex.list.data.remote.PokeListRemoteDataSource
import kotlinx.coroutines.flow.first

class PokeListRepository(
    private val local: PokeListLocalDataSource,
    private val remote: PokeListRemoteDataSource
) {
    suspend fun getPokeList(): Result<List<Poke>?> {
        return try {
            val localData = local.getPokemon().first()
            
            if (localData.isNotEmpty()) {
                updateFromRemote()
                return Result.success(localData)
            }
            val remoteResult = remote.getPokeList()
            if (remoteResult.isSuccess) {
                remoteResult.getOrNull()?.let { pokemons ->
                    Result.success(pokemons)
                } ?: Result.failure(NetworkErrorException("Response body is null"))
            } else {
                Result.failure(NetworkErrorException(remoteResult.exceptionOrNull()?.message))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            val localData = local.getPokemon().first()
            if (localData.isNotEmpty()) {
                Result.success(localData)
            } else {
                Result.failure(ex)
            }
        }
    }

    private suspend fun updateFromRemote() {
        try {
            val remoteResult = remote.getPokeList()
            if (remoteResult.isSuccess) {
                remoteResult.getOrNull()?.let { pokemons ->
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
