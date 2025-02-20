package com.example.pokedex

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


    private const val BASE_URL: String = "https://pokeapi.co/api/v2/"

    private val retrofitInstance : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api : ApiService = retrofitInstance.create(ApiService::class.java)

}