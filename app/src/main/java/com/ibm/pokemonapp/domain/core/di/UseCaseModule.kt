package com.ibm.pokemonapp.domain.core.di

import com.ibm.pokemonapp.domain.repository.PokemonRepository
import com.ibm.pokemonapp.domain.usecase.GetPokemonDetailsUseCase
import com.ibm.pokemonapp.domain.usecase.GetPokemonDetailsUseCaseImpl
import com.ibm.pokemonapp.domain.usecase.GetPokemonListUseCase
import com.ibm.pokemonapp.domain.usecase.GetPokemonListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun getPokemonList(pokemonRepository: PokemonRepository): GetPokemonListUseCase {
        return GetPokemonListUseCaseImpl(pokemonRepository)
    }

    @Provides
    fun getPokemonDetails(pokemonRepository: PokemonRepository): GetPokemonDetailsUseCase {
        return GetPokemonDetailsUseCaseImpl(pokemonRepository)
    }

}