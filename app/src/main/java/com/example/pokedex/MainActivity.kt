package com.example.pokedex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.pokedex.ui.theme.PokedexTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                var pokemonList by remember { mutableStateOf<List<PokeDto>>(emptyList()) }
                var isLoading by remember { mutableStateOf(true) }
                val apiSevice = RetrofitClient.retrofitInstance.create(ApiService::class.java)
                val callPokemon = apiSevice.getPokemonList()


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
                        isLoading = false
                    }

                    override fun onFailure(call: Call<PokeResponse>, t: Throwable) {
                        Log.d("MainActivity", "Network Error :: ${t.message}")
                        isLoading = false
                    }

                })

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isLoading) {
                        Text(text = "Loading Pokémon list...")
                    } else if (pokemonList.isEmpty()) {
                        Text(text = "No Pokémon found")
                    } else {
                        LazyColumn {
                            items(pokemonList) { pokemon ->
                                Text(text = pokemon.name)
                            }
                        }

                    }
                }
            }
        }
    }
}