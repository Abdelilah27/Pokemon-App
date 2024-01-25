package com.ibm.pokemonapp.data.source.network.response

import com.ibm.pokemonapp.data.source.network.response.model.Result

data class PokemonListResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)