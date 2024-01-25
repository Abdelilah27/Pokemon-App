package com.ibm.pokemonapp.data.source.network.response.model

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)