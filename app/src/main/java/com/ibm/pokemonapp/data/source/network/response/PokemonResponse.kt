package com.ibm.pokemonapp.data.source.network.response

import com.ibm.pokemonapp.data.source.network.response.model.Ability
import com.ibm.pokemonapp.data.source.network.response.model.Form
import com.ibm.pokemonapp.data.source.network.response.model.GameIndice
import com.ibm.pokemonapp.data.source.network.response.model.HeldItem
import com.ibm.pokemonapp.data.source.network.response.model.Move
import com.ibm.pokemonapp.data.source.network.response.model.Species
import com.ibm.pokemonapp.data.source.network.response.model.Sprites
import com.ibm.pokemonapp.data.source.network.response.model.Stat
import com.ibm.pokemonapp.data.source.network.response.model.Type

data class PokemonResponse(
    val abilities: List<Ability>? = listOf(),
    val base_experience: Int,
    val forms: List<Form>? = listOf(),
    val game_indices: List<GameIndice>? = listOf(),
    val height: Int,
    val held_items: List<HeldItem>? = listOf(),
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>? = listOf(),
    val name: String,
    val order: Int,
    val past_abilities: List<Any>,
    val past_types: List<Any>,
    val species: Species? = null,
    val sprites: Sprites? = null,
    val stats: List<Stat>,
    val types: List<Type>? = listOf(),
    val weight: Int
)