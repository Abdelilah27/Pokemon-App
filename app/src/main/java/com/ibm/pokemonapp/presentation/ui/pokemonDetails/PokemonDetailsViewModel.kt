package com.ibm.pokemonapp.presentation.ui.pokemonDetails

import androidx.lifecycle.ViewModel
import com.ibm.pokemonapp.data.source.network.response.PokemonResponse
import com.ibm.pokemonapp.domain.model.Resource
import com.ibm.pokemonapp.domain.usecase.GetPokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
) :
    ViewModel() {
    suspend fun getPokemonDetails(pokemonName: String): Flow<Resource<PokemonResponse>> =
        getPokemonDetailsUseCase(pokemonName)

}