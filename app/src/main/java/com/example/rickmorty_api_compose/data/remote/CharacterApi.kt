package com.example.rickmorty_api_compose.data.remote

import com.example.rickmorty_api_compose.data.remote.model.CharacterListRemote
import com.example.rickmorty_api_compose.data.remote.model.CharacterRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("/api/character")
    suspend fun readAll(@Query("name") name:String=""): Response<CharacterListRemote>

    @GET("/api/character/{id}")
    suspend fun readOne(@Path("id") id: Long): Response<CharacterRemote>

}