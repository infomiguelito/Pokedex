package com.example.pokedex.common.data.local

import androidx.room.TypeConverter
import com.example.pokedex.common.data.remote.model.PokeDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromTypeSlotList(value: List<PokeDto.TypeSlot>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTypeSlotList(value: String): List<PokeDto.TypeSlot> {
        val listType = object : TypeToken<List<PokeDto.TypeSlot>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromStatSlotList(value: List<PokeDto.StatSlot>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStatSlotList(value: String): List<PokeDto.StatSlot> {
        val listType = object : TypeToken<List<PokeDto.StatSlot>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromSprites(value: PokeDto.Sprites): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSprites(value: String): PokeDto.Sprites {
        return gson.fromJson(value, PokeDto.Sprites::class.java)
    }
} 