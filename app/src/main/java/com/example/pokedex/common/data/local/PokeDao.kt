package com.example.pokedex.common.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<PokeEntity>)

    @Update
    suspend fun updatePokemon(pokemon: PokeEntity)

    @Delete
    suspend fun deletePokemon(pokemon: PokeEntity)

    @Query("DELETE FROM pokeentity")
    suspend fun deleteAllPokemons()

    @Query("SELECT * FROM pokeentity")
    fun getAllPokemons(): Flow<List<PokeEntity>>

    @Query("SELECT * FROM pokeentity WHERE name = :pokemonName")
    suspend fun getPokemonByName(pokemonName: String): PokeEntity?

    @Query("SELECT * FROM pokeentity WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchPokemons(searchQuery: String): Flow<List<PokeEntity>>

    @Query("SELECT * FROM pokeentity WHERE types LIKE '%' || :type || '%'")
    fun getPokemonsByType(type: String): Flow<List<PokeEntity>>

    @Query("SELECT * FROM pokeentity ORDER BY height DESC LIMIT :limit")
    fun getTallestPokemons(limit: Int = 10): Flow<List<PokeEntity>>

    @Query("SELECT * FROM pokeentity ORDER BY weight DESC LIMIT :limit")
    fun getHeaviestPokemons(limit: Int = 10): Flow<List<PokeEntity>>

    @Query("SELECT COUNT(*) FROM pokeentity")
    suspend fun getPokemonCount(): Int

    @Query("SELECT COUNT(*) FROM pokeentity WHERE types LIKE '%' || :type || '%'")
    suspend fun getPokemonCountByType(type: String): Int

    @Query("SELECT * FROM PokeEntity WHERE name = :id")
    fun getPokemonById(id: String): Flow<PokeEntity?>
}