package com.ibm.pokemonapp.domain.usecase

import com.ibm.pokemonapp.data.source.network.response.PokemonListResponse
import com.ibm.pokemonapp.domain.usecase.base.BaseUseCase

interface GetPokemonListUseCase : BaseUseCase<Pair<Int, Int>, PokemonListResponse>