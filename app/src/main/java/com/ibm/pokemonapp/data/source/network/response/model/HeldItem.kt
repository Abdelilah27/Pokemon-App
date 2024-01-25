package com.ibm.pokemonapp.data.source.network.response.model

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)