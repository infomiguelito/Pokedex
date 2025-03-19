package com.example.pokedex.detail.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.common.data.remote.model.PokeDto
import com.example.pokedex.detail.presentation.PokeDetailUiState
import com.example.pokedex.detail.presentation.PokeDetailViewModel

@Composable
fun PokeDetailScreen(
    pokemonId: String,
    viewModel: PokeDetailViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetails(pokemonId)
    }

    when (uiState) {
        is PokeDetailUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is PokeDetailUiState.Success -> {
            PokemonDetailContent(
                pokemon = (uiState as PokeDetailUiState.Success).pokemon,
                modifier = modifier
            )
        }
        is PokeDetailUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (uiState as PokeDetailUiState.Error).message,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun PokemonDetailContent(
    pokemon: PokeDto,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = pokemon.sprites.front_default,
            contentDescription = pokemon.name,
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )

        Text(
            text = pokemon.name.capitalize(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Tipos: ${pokemon.types.joinToString(", ") { it.type.name.capitalize() }}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = "Altura: ${pokemon.height / 10.0}m",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = "Peso: ${pokemon.weight / 10.0}kg",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = "Status:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        pokemon.stats.forEach { stat ->
            Text(
                text = "${stat.stat.name.capitalize()}: ${stat.base_stat}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
} 