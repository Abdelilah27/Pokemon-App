package com.ibm.pokemonapp.domain.repository

import com.ibm.pokemonapp.data.source.network.response.PokemonListResponse
import com.ibm.pokemonapp.data.source.network.response.PokemonResponse
import com.ibm.pokemonapp.domain.model.Resource


interface PokemonRepository {
    suspend fun getPokemonList(params: Pair<Int, Int>): Resource<PokemonListResponse>
    suspend fun getPokemonDetails(pokemonName: String): Resource<PokemonResponse>

}