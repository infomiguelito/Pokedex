package com.example.pokedex.list.data

import android.accounts.NetworkErrorException
import android.util.Log
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
            Log.d("PokeListRepository", "Fetching Pokemon list")
            val localData = local.getPokemon().first()
            
            if (localData.isNotEmpty()) {
                Log.d("PokeListRepository", "Returning ${localData.size} Pokemon from local database")
                updateFromRemote()
                Result.success(localData)
            } else {
                Log.d("PokeListRepository", "Local database empty, fetching from remote")
                val remoteResult = remote.getPokeList()
                remoteResult.onSuccess { pokemons ->
                    pokemons?.let { local.savePokemonList(it) }
                }
                remoteResult
            }
        } catch (ex: Exception) {
            Log.e("PokeListRepository", "Error fetching Pokemon list", ex)
            val localData = local.getPokemon().first()
            if (localData.isNotEmpty()) {
                Log.d("PokeListRepository", "Returning ${localData.size} Pokemon from local database after error")
                Result.success(localData)
            } else {
                Result.failure(ex)
            }
        }
    }

    private suspend fun updateFromRemote() {
        try {
            Log.d("PokeListRepository", "Updating from remote")
            val remoteResult = remote.getPokeList()
            remoteResult.onSuccess { pokemons ->
                pokemons?.let {
                    Log.d("PokeListRepository", "Saving ${it.size} Pokemon to local database")
                    local.savePokemonList(it)
                }
            }.onFailure { error ->
                Log.e("PokeListRepository", "Error updating from remote", error)
            }
        } catch (ex: Exception) {
            Log.e("PokeListRepository", "Exception updating from remote", ex)
        }
    }
}
