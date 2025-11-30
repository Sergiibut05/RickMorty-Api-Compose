package com.example.rickmorty_api_compose.data

import com.example.rickmorty_api_compose.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterDataSource {

    suspend fun addAll(pokemonList: List<Character>)

    fun observe(): Flow<Result<List<Character>>>

    suspend fun readAll(): Result<List<Character>>

    suspend fun readOne(id: Long): Result<Character>

    suspend fun isError()
}