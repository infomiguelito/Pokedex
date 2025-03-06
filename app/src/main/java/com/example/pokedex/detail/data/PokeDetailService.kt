package com.example.pokedex.detail.data

import com.example.pokedex.common.model.PokeDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeDetailService {
    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: String): Response<PokeDto>
}