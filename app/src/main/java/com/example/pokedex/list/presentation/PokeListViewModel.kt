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
import com.example.pokedex.list.presentation.ui.PokeListUiState
import com.example.pokedex.list.presentation.ui.PokeUiData
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

    private val getPokemonList = MutableStateFlow(PokeListUiState())
    val pokemonList: StateFlow<PokeListUiState> = getPokemonList

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        getPokemonList.value = PokeListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = pokeListService.getPokemonList()
                if (response.isSuccessful) {
                    val pokedex = response.body()?.results
                    if (pokedex != null) {
                       val pokeUiDataList = pokedex.map { pokeDto ->
                            PokeUiData(
                                id = pokeDto.id,
                                name = pokeDto.name,
                                image = pokeDto.frontFullDefault,
                                sprites = pokeDto.sprites,
                            )
                        }
                        getPokemonList.value = PokeListUiState(list = pokeUiDataList)
                    }
                } else {
                    getPokemonList.value = PokeListUiState(isError = true)
                    Log.d("PokeListViewModel", "Request Error :: ${response.errorBody()}")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                getPokemonList.value = PokeListUiState(isError = true)
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