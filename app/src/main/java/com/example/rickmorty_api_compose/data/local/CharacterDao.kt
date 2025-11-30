package com.example.rickmorty_api_compose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity): Long

    @Delete
    suspend fun deleteOne(character: CharacterEntity): Int

    @Query("SELECT * FROM character")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM character")
    fun observeAll(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun readCharacterById(id: Long): CharacterEntity?
}