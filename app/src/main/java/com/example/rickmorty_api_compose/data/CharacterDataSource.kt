package com.example.rickmorty_api_compose.data

import com.example.rickmorty_api_compose.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterDataSource {

    suspend fun addAll(pokemonList: List<Character>)

    fun observe(): Flow<Result<List<Character>>>

    suspend fun readAll(name: String? = ""): Result<List<Character>>

    suspend fun readOne(id: Long): Result<Character>

    suspend fun deleteOne(character: Character): Result<Int>

    suspend fun deleteAll(): Result<Int>

    suspend fun isError()
}