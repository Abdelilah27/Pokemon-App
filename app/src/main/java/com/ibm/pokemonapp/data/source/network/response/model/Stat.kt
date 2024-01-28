package com.ibm.pokemonapp.data.source.network.response.model

data class Stat(
    val base_stat: Int,
    val effort: Int? = 0,
    val stat: StatX? = null
)