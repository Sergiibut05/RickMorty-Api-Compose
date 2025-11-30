package com.example.rickmorty_api_compose.data.remote.model

data class CharacterListRemote(
    val results: List<CharacterRemote>
)

data class CharacterRemote(
    val id: Long,
    val name: String,
    val image: String,
    val origin: CharacterOrigin
)

data class CharacterOrigin(
    val name: String
)