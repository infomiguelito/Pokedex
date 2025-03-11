package com.example.pokedex.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedex.common.data.RetrofitClient
import com.example.pokedex.common.model.PokeDto
import com.example.pokedex.list.data.PokeListService
import com.example.pokedex.list.presentation.ui.PokeListUiState
import com.example.pokedex.list.presentation.ui.PokeUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class PokeListViewModel(
    private val pokeListService: PokeListService
) : ViewModel() {

    private val getPokemonList = MutableStateFlow(PokeListUiState())
    val pokemonList: StateFlow<PokeListUiState> = getPokemonList

    init {
        fetchPokemonList()
    }

    fun fetchPokemonList() {
        getPokemonList.value = PokeListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = pokeListService.getPokemonList()
                if (response.isSuccessful) {
                    val pokeListResponse = response.body()
                    if (pokeListResponse != null) {
                        val pokeUiDataList = pokeListResponse.results.map { pokemon ->
                            PokeUiData(
                                id = pokemon.id,
                                name = pokemon.name,
                                image = pokemon.image,
                                sprites = PokeDto.Sprites(front_default = pokemon.image)
                            )
                        }
                        getPokemonList.value = PokeListUiState(list = pokeUiDataList)
                    } else {
                        getPokemonList.value = PokeListUiState(
                            isError = true,
                            errorMessage = "Não foi possível carregar os Pokémon"
                        )
                    }
                } else {
                    getPokemonList.value = PokeListUiState(
                        isError = true,
                        errorMessage = "Erro ao carregar os Pokémon. Tente novamente."
                    )
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (ex is UnknownHostException) {
                    getPokemonList.value = PokeListUiState(
                        isError = true,
                        errorMessage = "Sem conexão com a internet"
                    )
                } else {
                    getPokemonList.value = PokeListUiState(
                        isError = true,
                        errorMessage = "Erro ao processar dados: ${ex.message}"
                    )
                }
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