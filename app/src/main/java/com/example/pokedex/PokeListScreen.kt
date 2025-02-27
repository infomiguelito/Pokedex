package com.example.pokedex

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun PokeListScreen(navController: NavHostController) {

    var pokemonList by remember { mutableStateOf<List<PokeDto>>(emptyList()) }
    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
    val callPokemon = apiService.getPokemonList()


    callPokemon.enqueue(object : Callback<PokeResponse> {
        override fun onResponse(
            call: Call<PokeResponse>,
            response: Response<PokeResponse>
        ) {
            if (response.isSuccessful) {
                val pokedex = response.body()?.results
                if (pokedex != null) {
                    pokemonList = pokedex
                }
            } else {
                Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
            }
        }

        override fun onFailure(call: Call<PokeResponse>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }

    })

    PokeListContent(
        pokemonList = pokemonList
    ) { itemClicked ->
        navController.navigate(route = "pokeDetail/${itemClicked.id}")

    }
}

@Composable
private fun PokeListContent(
    pokemonList: List<PokeDto>,
    onClick: (PokeDto) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PokeGrid(
            pokemonList,
            onCLick = onClick
        )
    }
}

@Composable
private fun PokeCard(
    pokeDto: PokeDto,
    onCLick: (PokeDto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = pokeDto.frontFullDefault,
                contentDescription = pokeDto.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Text(
                text = pokeDto.name.capitalize(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Button(
                onClick = {onCLick(pokeDto)},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Ver Detalhes")
            }
        }
    }
}

@Composable
private fun PokeGrid(
    pokeList: List<PokeDto>,
    onCLick: (PokeDto) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pokeList) { poke ->
            PokeCard(
                poke,
                onCLick = onCLick
            )
        }
    }
}

