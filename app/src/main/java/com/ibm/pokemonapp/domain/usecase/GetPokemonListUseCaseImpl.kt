package com.ibm.pokemonapp.domain.usecase

import com.ibm.pokemonapp.data.source.network.response.PokemonListResponse
import com.ibm.pokemonapp.domain.model.Resource
import com.ibm.pokemonapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetPokemonListUseCaseImpl
@Inject constructor(var pokemonRepository: PokemonRepository) : GetPokemonListUseCase {
    override suspend fun invoke(param: Pair<Int, Int>): Flow<Resource<PokemonListResponse>> {
        return flowOf(pokemonRepository.getPokemonList(param))
    }
}