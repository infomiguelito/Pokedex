package com.example.pokedex

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit: Int = 20): Call<PokeResponse>

    @GET("pokemon/{id}")
     fun getPokemonDetail(@Path("id") id: Int): Call<PokeResponse>

}