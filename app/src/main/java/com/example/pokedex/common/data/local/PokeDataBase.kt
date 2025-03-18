package com.example.pokedex.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database([PokeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class PokeDataBase: RoomDatabase() {

    abstract fun getPokeDao(): PokeDao

}