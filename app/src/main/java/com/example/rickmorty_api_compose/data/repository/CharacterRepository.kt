package com.example.rickmorty_api_compose.data.repository

import com.example.rickmorty_api_compose.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun readOne(id: Long): Result<Character>
    suspend fun readAll(): Result<List<Character>>
    fun observe(): Flow<Result<List<Character>>>
}