package com.example.pokedex

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokeDetailScreen(pokeId: String) {
    Column {
        Text(text = pokeId)

    }
    PokeDetailContent()
}

@Composable
private fun PokeDetailContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Poke Detail"
        )

    }


}

@Preview(showBackground = true)
@Composable
private fun PokeDetailPreview() {
    PokeDetailContent()
}