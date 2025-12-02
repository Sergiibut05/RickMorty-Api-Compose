package com.example.rickmorty_api_compose.data.repository

import com.example.rickmorty_api_compose.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun readOne(id: Long): Result<Character>
    suspend fun readAll(text: String): Result<List<Character>>
    fun observe(): Flow<Result<List<Character>>>
    suspend fun deleteOne(character: Character): Result<Int>
    suspend fun deleteAll(): Result<Int>

    suspend fun addAll(list: List<Character>)
}