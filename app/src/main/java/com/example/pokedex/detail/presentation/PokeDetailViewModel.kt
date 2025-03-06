package com.example.pokedex.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedex.common.data.RetrofitClient
import com.example.pokedex.common.model.PokeDto
import com.example.pokedex.detail.data.PokeDetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeDetailViewModel(
    private val pokeDetailService: PokeDetailService
) : ViewModel() {

    private val _getPokemonDetail = MutableStateFlow<PokeDto?>(null)
    val pokemonDetail: StateFlow<PokeDto?> = _getPokemonDetail

    fun fetchPokemonDetail(pokeId: String) {
        if (_getPokemonDetail.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = pokeDetailService.getPokemonDetails(pokeId)
                if (response.isSuccessful) {
                    _getPokemonDetail.value = response.body()
                } else {
                    Log.d("PokeDetailViewModel", "Request Error :: ${response.errorBody()}")
                }
            }
        }
    }

    fun cleanPokeId() {
        viewModelScope.launch {
            delay(1000)
            _getPokemonDetail.value = null
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(PokeDetailService::class.java)
                return PokeDetailViewModel(
                    detailService
                ) as T
            }
        }
    }
}