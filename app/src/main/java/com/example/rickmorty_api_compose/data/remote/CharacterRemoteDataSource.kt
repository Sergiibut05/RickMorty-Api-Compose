package com.example.rickmorty_api_compose.data.remote

import com.example.rickmorty_api_compose.data.CharacterDataSource
import com.example.rickmorty_api_compose.data.remote.model.CharacterRemote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import com.example.rickmorty_api_compose.data.model.Character

class CharacterRemoteDataSource @Inject constructor(
    private val api: CharacterApi,
    private val scope: CoroutineScope
): CharacterDataSource{
    override suspend fun addAll(pokemonList: List<Character>) {
        TODO("Not yet implemented")
    }

    override fun observe(): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.success(listOf<Character>()))
            val result = readAll()
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L), // Mantiene el flow vivo 5 segundos después de que la UI deje de escuchar
            replay = 1 // Repite el último valor a nuevos suscriptores
        )
    }

    override suspend fun readAll(): Result<List<Character>> {
        try {
            val response = api.readAll()
            val finalList = mutableListOf<Character>()

            return if (response.isSuccessful) {
                val body = response.body()!!
                for (result in body.results) {
                    val remotePokemon = result.toExternal()
                    remotePokemon?.let {
                        finalList.add(it)
                    }
                }
                Result.success(finalList)
            } else {
                Result.failure(RuntimeException("Error code: ${response.code()}"))
            }
        }catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun readOne(id: Long): Result<Character> {
        try {
            val response = api.readOne(id)
            return if (response.isSuccessful) {
                val character = response.body()!!.toExternal()
                Result.success(character)
            } else {
                Result.failure(RuntimeException("Error code: ${response.code()}"))
            }
        }catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun deleteOne(character: Character): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    fun CharacterRemote.toExternal(): Character {
        return Character(
            id = this.id,
            name = this.name,
            image = this.image,
            originName = this.origin.name,
        )
    }


}