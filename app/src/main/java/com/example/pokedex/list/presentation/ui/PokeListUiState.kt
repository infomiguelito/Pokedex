package com.example.pokedex.list.presentation.ui

import com.example.pokedex.common.model.PokeDto.Sprites

data class PokeListUiState(
    val list : List<PokeUiData> = emptyList(),
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val errorMessage: String? = "something went wrong",
)


data class PokeUiData(
    val name: String,
    val id : String,
    val sprites: Sprites,
    val image: String,
)