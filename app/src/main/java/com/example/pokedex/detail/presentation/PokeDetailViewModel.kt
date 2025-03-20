package com.example.pokedex.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedex.PokedexApplication
import com.example.pokedex.common.data.remote.model.PokeDto
import com.example.pokedex.common.data.remote.RetrofitClient
import com.example.pokedex.detail.data.PokeDetailRepository
import com.example.pokedex.detail.data.local.PokeDetailLocalDataSource
import com.example.pokedex.detail.data.remote.PokeDetailRemoteDataSource
import com.example.pokedex.detail.data.remote.PokeDetailService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokeDetailViewModel(
    private val repository: PokeDetailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokeDetailUiState>(PokeDetailUiState.Loading)
    val uiState: StateFlow<PokeDetailUiState> = _uiState.asStateFlow()

    fun loadPokemonDetails(id: String) {
        viewModelScope.launch {
            _uiState.value = PokeDetailUiState.Loading
            try {
                val result = repository.getPokemonDetails(id)
                result.onSuccess { pokemon ->
                    if (pokemon != null) {
                        _uiState.value = PokeDetailUiState.Success(pokemon)
                    } else {
                        _uiState.value = PokeDetailUiState.Error("Pokemon não encontrado")
                    }
                }.onFailure { error ->
                    _uiState.value = PokeDetailUiState.Error(error.message ?: "Erro desconhecido")
                }
            } catch (e: Exception) {
                _uiState.value = PokeDetailUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as PokedexApplication
                return PokeDetailViewModel(application.detailRepository) as T
            }
        }
    }
}

sealed class PokeDetailUiState {
    object Loading : PokeDetailUiState()
    data class Success(val pokemon: PokeDto) : PokeDetailUiState()
    data class Error(val message: String) : PokeDetailUiState()
}