package com.example.rickmorty_api_compose.data.repository

import com.example.rickmorty_api_compose.data.CharacterDataSource
import com.example.rickmorty_api_compose.data.model.Character
import com.example.rickmorty_api_compose.di.LocalDataSource
import com.example.rickmorty_api_compose.di.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Scope
import kotlin.collections.addAll

class CharacterRespositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: CharacterDataSource,
    @LocalDataSource private val localDataSource: CharacterDataSource,
    private val scope: CoroutineScope
): CharacterRepository {
    override suspend fun readOne(id: Long): Result<Character> {
        return remoteDataSource.readOne(id)
    }

    override suspend fun readAll(text: String): Result<List<Character>> {
        return remoteDataSource.readAll(text)
    }

    override fun observe(): Flow<Result<List<Character>>> {
        scope.launch {
            refresh() // Dispara la actualización en background
        }
        // La UI solo escucha a la BD. Si refresh() actualiza la BD, este Flow emitirá los nuevos datos automáticamente.
        return localDataSource.observe()
    }

    override suspend fun deleteOne(character: Character): Result<Int> {
        return localDataSource.deleteOne(character)
    }

    override suspend fun deleteAll(): Result<Int> {
        return localDataSource.deleteAll()
    }


    private suspend fun refresh() {
        val resultRemoteCharacter = remoteDataSource.readAll()
        if (resultRemoteCharacter.isSuccess) {
            // Guardar en local disparará el Flow de 'observe()'
            localDataSource.addAll(resultRemoteCharacter.getOrNull()!!)
        }
    }
    override suspend fun addAll(list: List<Character>) {
        localDataSource.addAll(list)
    }

}