package com.example.rickmorty_api_compose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 1)
abstract class RickDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao
}