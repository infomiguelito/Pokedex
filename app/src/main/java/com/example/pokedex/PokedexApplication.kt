package com.example.pokedex

import android.app.Application
import androidx.room.Room
import com.example.pokedex.common.data.remote.RetrofitClient
import com.example.pokedex.common.data.local.PokeDataBase
import com.example.pokedex.list.data.PokeListRepository
import com.example.pokedex.list.data.local.PokeListLocalDataSource
import com.example.pokedex.list.data.remote.PokeListRemoteDataSource
import com.example.pokedex.list.data.remote.PokeListService
import com.example.pokedex.detail.data.PokeDetailRepository
import com.example.pokedex.detail.data.local.PokeDetailLocalDataSource
import com.example.pokedex.detail.data.remote.PokeDetailRemoteDataSource
import com.example.pokedex.detail.data.remote.PokeDetailService

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

    val detailRepository: PokeDetailRepository by lazy {
        val detailService = RetrofitClient.retrofitInstance.create(PokeDetailService::class.java)
        val localDataSource = PokeDetailLocalDataSource(db.getPokeDao())
        val remoteDataSource = PokeDetailRemoteDataSource(detailService)
        PokeDetailRepository(localDataSource, remoteDataSource)
    }

}