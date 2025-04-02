package com.example.pokedex.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedex.PokedexApplication
import com.example.pokedex.common.data.remote.RetrofitClient
import com.example.pokedex.common.data.remote.model.PokeDto
import com.example.pokedex.list.data.PokeListRepository
import com.example.pokedex.list.data.remote.PokeListService
import com.example.pokedex.list.presentation.ui.PokeListUiState
import com.example.pokedex.list.presentation.ui.PokeUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class PokeListViewModel(
    private val repository: PokeListRepository
) : ViewModel() {

    private val getPokemonList = MutableStateFlow(PokeListUiState())
    val pokemonList: StateFlow<PokeListUiState> = getPokemonList

    init {
        fetchPokemonList()
    }

    fun fetchPokemonList() {
        getPokemonList.value = PokeListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getPokeList()
            getPokemonList.value = when {
                result.isSuccess -> {
                    val pokeListResponse = result.getOrNull()
                    if (pokeListResponse != null) {
                        val pokeUiDataList = pokeListResponse.map { pokemon ->
                            PokeUiData(
                                id = pokemon.id,
                                name = pokemon.name,
                                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png",
                                sprites = PokeDto.Sprites(front_default = pokemon.image)
                            )
                        }
                        viewModelScope.launch(Dispatchers.IO) {
                            repository.updateFromRemote()
                        }
                        PokeListUiState(list = pokeUiDataList)
                    } else {
                        PokeListUiState(
                            isError = true,
                            errorMessage = "Não foi possível carregar os Pokémon"
                        )
                    }
                }
                else -> {
                    val ex = result.exceptionOrNull()
                    PokeListUiState(
                        isError = true,
                        errorMessage = when (ex) {
                            is UnknownHostException -> "Sem conexão com a internet"
                            else -> "Erro ao processar dados: ${ex?.message}"
                        }
                    )
                }
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService =
                    RetrofitClient.retrofitInstance.create(PokeListService::class.java)
                val application = checkNotNull(extras[APPLICATION_KEY])
                return PokeListViewModel(
                    repository = (application as PokedexApplication).repository
                ) as T
            }
        }
    }
}
