package com.example.pokedex

import android.app.Application
import androidx.room.Room
import com.example.pokedex.common.data.RetrofitClient
import com.example.pokedex.common.data.local.PokeDataBase
import com.example.pokedex.list.data.PokeListRepository
import com.example.pokedex.list.data.local.PokeListLocalDataSource
import com.example.pokedex.list.data.remote.PokeListRemoteDataSource
import com.example.pokedex.list.data.remote.PokeListService

class PokedexApplication : Application(){

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            PokeDataBase::class.java, "database-pokedex"
        ).build()
    }

    private val listService by lazy {
        RetrofitClient.retrofitInstance.create(PokeListService::class.java)
    }

    private val localDataSource: PokeListLocalDataSource by lazy {
        PokeListLocalDataSource(db.getPokeDao())
    }

    private val remoteDataSource: PokeListRemoteDataSource by lazy {
        PokeListRemoteDataSource(listService)
    }

    val repository: PokeListRepository by lazy {
        PokeListRepository(
            local = localDataSource,
            remote = remoteDataSource
        )
    }

}