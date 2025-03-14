package com.example.pokedex.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([PokeEntity::class], version = 1)
abstract class PokeDataBase: RoomDatabase() {

    abstract fun getPokeDao(): PokeDao

}