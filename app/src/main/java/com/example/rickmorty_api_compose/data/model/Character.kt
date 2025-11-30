package com.example.rickmorty_api_compose.data.model

data class Character(
    val id:Long,
    val name:String,
    val image: String,
    val originName: String
)