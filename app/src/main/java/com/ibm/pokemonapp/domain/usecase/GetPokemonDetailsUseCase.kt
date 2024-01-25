package com.ibm.pokemonapp.domain.usecase

import com.ibm.pokemonapp.data.source.network.response.PokemonResponse
import com.ibm.pokemonapp.domain.usecase.base.BaseUseCase

interface GetPokemonDetailsUseCase : BaseUseCase<String, PokemonResponse>