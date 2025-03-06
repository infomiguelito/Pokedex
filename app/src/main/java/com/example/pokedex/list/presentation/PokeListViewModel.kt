package com.example.pokedex.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedex.common.data.RetrofitClient
import com.example.pokedex.common.model.PokeDto
import com.example.pokedex.common.model.PokeResponse
import com.example.pokedex.list.data.PokeListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeListViewModel(
    private val pokeListService: PokeListService
) : ViewModel() {

    private val getPokemonList = MutableStateFlow<List<PokeDto>>(emptyList())
    val pokemonList: StateFlow<List<PokeDto>> = getPokemonList

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = pokeListService.getPokemonList()
            if (response.isSuccessful) {
                val pokedex = response.body()?.results
                if (pokedex != null) {
                    getPokemonList.value = pokedex
                }
            } else {
                Log.d("PokeListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService =
                    RetrofitClient.retrofitInstance.create(PokeListService::class.java)
                return PokeListViewModel(
                    listService
                ) as T
            }
        }
    }
}