package com.ibm.pokemonapp.domain.usecase

import com.ibm.pokemonapp.data.source.network.response.PokemonResponse
import com.ibm.pokemonapp.domain.model.Resource
import com.ibm.pokemonapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetPokemonDetailsUseCaseImpl
@Inject constructor(var pokemonRepository: PokemonRepository) : GetPokemonDetailsUseCase {
    override suspend fun invoke(param: String): Flow<Resource<PokemonResponse>> {
        return flowOf(pokemonRepository.getPokemonDetails(param))
    }
}