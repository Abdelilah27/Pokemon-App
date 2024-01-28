package com.ibm.pokemonapp.presentation.ui.pokemonDetails

import androidx.lifecycle.ViewModel
import com.ibm.pokemonapp.data.source.network.response.PokemonResponse
import com.ibm.pokemonapp.data.source.network.response.model.Stat
import com.ibm.pokemonapp.domain.model.Resource
import com.ibm.pokemonapp.domain.usecase.GetPokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
) :
    ViewModel() {

    // Fetch Pokemon details and return as a Flow
    suspend fun getPokemonDetails(pokemonName: String): Flow<Resource<PokemonResponse>> =
        getPokemonDetailsUseCase(pokemonName)


    // Calculate and return the global state percentage based on Pokemon stats
    fun calculateGlobalState(stats: List<Stat>): Float {
        try {
            val totalStats = stats.sumOf { it.base_stat }
            val maxPossibleStats = stats.size * 255
            val percentage = totalStats / maxPossibleStats.toDouble()
            return percentage.toFloat()
        } catch (e: ArithmeticException) {
            // Handle division by zero or any other arithmetic exceptions
            e.printStackTrace()
            return 0.0f
        }
    }
}