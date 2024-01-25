package com.ibm.pokemonapp.data.core.di

import com.ibm.pokemonapp.data.repository.PokemonRepositoryImp
import com.ibm.pokemonapp.data.source.network.api.ApiService
import com.ibm.pokemonapp.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun providesPokemonRepository(apiService: ApiService): PokemonRepository {
        return PokemonRepositoryImp(apiService)
    }
}