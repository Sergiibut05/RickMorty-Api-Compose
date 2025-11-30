package com.turingalan.pokemon.di

import android.content.Context
import androidx.room.Room
import com.example.rickmorty_api_compose.data.local.CharacterDao
import com.example.rickmorty_api_compose.data.local.RickDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): RickDatabase {

        val database = Room.databaseBuilder(context = applicationContext,
            RickDatabase::class.java,
            name = "rick-db").build()
        return database
    }

    @Provides
    fun providePokemonDao(
        database: RickDatabase
    ): CharacterDao {
        return database.getCharacterDao()
    }
}