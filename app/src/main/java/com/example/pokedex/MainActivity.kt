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
                var Pokemon by remember {
                    mutableStateOf<List<PokeDto>>(emptyList())
                }
                val apiSevice = RetrofitClient.retrofitInstance.create(ApiService::class.java)
                val callPokemon = apiSevice.getPokemon("pikachu")


                callPokemon.enqueue(object : Callback<PokeResponse> {
                    override fun onResponse(
                        call: Call<PokeResponse>,
                        response: Response<PokeResponse>
                    ) {
                        if (response.isSuccessful) {
                            val pokedex = response.body()?.results
                            if (pokedex != null){
                                Pokemon = pokedex
                            }
                        } else {
                            Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<PokeResponse>, t: Throwable) {
                        Log.d("MainActivity", "Network Error :: ${t.message}")
                    }

                })

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn {
                        items(Pokemon){
                            Text(text = it.name)
                        }
                    }

                }
            }
        }
    }
}