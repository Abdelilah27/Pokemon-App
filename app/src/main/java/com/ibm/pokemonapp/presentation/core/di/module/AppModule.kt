package com.ibm.pokemonapp.presentation.core.di.module

import com.ibm.pokemonapp.presentation.core.di.PokemonApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun application(): PokemonApplication {
        return PokemonApplication()
    }

}