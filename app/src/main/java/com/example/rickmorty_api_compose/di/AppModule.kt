package com.example.rickmorty_api_compose.di

import com.example.rickmorty_api_compose.data.CharacterDataSource
import com.example.rickmorty_api_compose.data.local.CharacterLocalDataSource
import com.example.rickmorty_api_compose.data.remote.CharacterRemoteDataSource
import com.example.rickmorty_api_compose.data.repository.CharacterRepository
import com.example.rickmorty_api_compose.data.repository.CharacterRespositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton




@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindsRemoteDataSource(ds: CharacterRemoteDataSource): CharacterDataSource

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindsLocalDataSource(ds: CharacterLocalDataSource): CharacterDataSource

    @Binds
    @Singleton
    abstract  fun bindCharacterRepository(repository: CharacterRespositoryImpl): CharacterRepository

}
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource