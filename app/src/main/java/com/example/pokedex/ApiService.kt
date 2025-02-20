package com.example.pokedex

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Call<PokeResponse>
}