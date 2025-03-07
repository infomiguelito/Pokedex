package com.example.pokedex.list.data

import com.example.pokedex.common.model.PokeResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeListService {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int = 20): Response<PokeResponse>
}