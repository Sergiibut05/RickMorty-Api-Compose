package com.example.rickmorty_api_compose.data.local

import com.example.rickmorty_api_compose.data.CharacterDataSource
import com.example.rickmorty_api_compose.data.model.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map


import javax.inject.Inject

class CharacterLocalDataSource @Inject constructor(
    private val scope: CoroutineScope,
    private val characterDao: CharacterDao
): CharacterDataSource{
     override suspend fun addAll(pokemonList: List<Character>) {
        pokemonList.forEach { pokemon ->
            val entity = pokemon.toEntity()

            withContext(Dispatchers.IO) {
                characterDao.insert(entity)
            }
        }
    }

    override fun observe(): Flow<Result<List<Character>>> {
        val databaseFlow = characterDao.observeAll()

        return databaseFlow.map { entities ->
            Result.success(entities.toModel())
        }

    }

    override suspend fun readAll(): Result<List<Character>> {
        val result = Result.success(characterDao.getAll().toModel())
        return result
    }

    override suspend fun readOne(id: Long): Result<Character> {
        val entity = characterDao.readCharacterById(id)
        return if(entity == null){
            Result.failure(CharacterNotFoundException())
        } else{
            Result.success(entity.toModel())
        }
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

}