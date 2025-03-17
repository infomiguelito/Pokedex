package com.example.pokedex.detail.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.common.data.remote.model.PokeDto
import com.example.pokedex.detail.presentation.PokeDetailViewModel

@Composable
fun PokeDetailScreen(
    pokeId: String,
    navHostController: NavHostController,
    detailViewModel: PokeDetailViewModel
) {
    val pokeDto by detailViewModel.pokemonDetail.collectAsState()
    detailViewModel.fetchPokemonDetail(pokeId)

    Box(modifier = Modifier.fillMaxSize()) {
        pokeDto?.let {

            IconButton(
                onClick = {
                    detailViewModel.cleanPokeId()
                    navHostController.popBackStack()
                }) {
                Image(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Button"
                )
            }

            PokeDetailContent(it)
        } ?: CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun PokeDetailContent(poke: PokeDto) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        AsyncImage(
            model = poke.sprites.front_default,
            contentDescription = poke.name,
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Text(
            text = poke.name.capitalize(),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            poke.types.forEach { type ->
                Chip(type.type.name)
            }
        }

        Text(
            text = "Height: ${poke.height / 10.0}m | Weight: ${poke.weight / 10.0}kg",
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )



        Text(
            text = "Base Stats",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        poke.stats.forEach { stat ->
            StatBar(stat.stat.name.capitalize(), stat.base_stat)
        }
    }


}

@Composable
fun Chip(text: String) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(getPokemonColor(text), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = text.capitalize(), color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatBar(statName: String, value: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = statName, fontWeight = FontWeight.Bold)
        LinearProgressIndicator(
            progress = value / 300f,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = Color.Red
        )
    }
}

fun getPokemonColor(type: String): Color {
    return when (type.lowercase()) {
        "fire" -> Color.Red
        "water" -> Color.Blue
        "grass" -> Color.Green
        "electric" -> Color.Yellow
        else -> Color.Gray
    }
}
