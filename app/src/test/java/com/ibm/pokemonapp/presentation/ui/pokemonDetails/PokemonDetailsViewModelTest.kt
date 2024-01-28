package com.ibm.pokemonapp.presentation.ui.pokemonDetails

import com.ibm.pokemonapp.data.source.network.response.PokemonResponse
import com.ibm.pokemonapp.data.source.network.response.model.Stat
import com.ibm.pokemonapp.domain.model.Resource
import com.ibm.pokemonapp.domain.usecase.GetPokemonDetailsUseCase
import com.ibm.pokemonapp.presentation.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokemonDetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PokemonDetailsViewModel

    private lateinit var getPokemonDetailsUseCase: GetPokemonDetailsUseCase

    private val pokemonName = "spearow"

    @Before
    fun setup() {
        getPokemonDetailsUseCase = mockk(relaxed = true)
    }

    @Test
    fun `getPokemonDetails should update viewModel properties on success`() = runTest {
        // Arrange
        val mockResponse = createMockPokemonResponse()
        val getPokemonDetailsUseCase = mockk<GetPokemonDetailsUseCase>(relaxed = true)
        coEvery { getPokemonDetailsUseCase.invoke(any()) } returns flowOf(
            Resource.Success(
                mockResponse
            )
        )

        // Act
        viewModel = PokemonDetailsViewModel(getPokemonDetailsUseCase)
        val result = viewModel.getPokemonDetails(pokemonName).single()

        // Assert
        assertTrue(result is Resource.Success)
        assertEquals(mockResponse, (result as Resource.Success).data)
    }


    @Test
    fun `getPokemonDetails should handle error response`() = runTest {
        // Arrange
        val getPokemonDetailsUseCase = mockk<GetPokemonDetailsUseCase>(relaxed = true)
        coEvery { getPokemonDetailsUseCase.invoke(any()) } returns flowOf(
            Resource.Error(
                500, "An error occurred"
            )
        )

        // Act
        viewModel = PokemonDetailsViewModel(getPokemonDetailsUseCase)
        val result = viewModel.getPokemonDetails(pokemonName).single()

        // Assert
        assertTrue(result is Resource.Error)
        assertEquals("An error occurred", (result as Resource.Error).message)
    }

    @Test
    fun `calculateGlobalState should return correct percentage`() = runTest {
        // Arrange
        val mockResponse = createMockPokemonResponse()
        val getPokemonDetailsUseCase = mockk<GetPokemonDetailsUseCase>(relaxed = true)
        coEvery { getPokemonDetailsUseCase.invoke(any()) } returns flowOf(
            Resource.Success(
                mockResponse
            )
        )

        // Act
        viewModel = PokemonDetailsViewModel(getPokemonDetailsUseCase)
        val pokemonList = viewModel.getPokemonDetails(pokemonName).single()

        // Act
        val result = pokemonList.data?.let { viewModel.calculateGlobalState(it.stats) }

        // Assert
        result?.let { assertEquals(0.23529412f, it) }
    }

    // Helper Function: to create a mock PokemonResponse for testing purposes
    private fun createMockPokemonResponse(): PokemonResponse {
        return PokemonResponse(
            base_experience = 100,
            height = 150,
            id = 25,
            is_default = true,
            location_area_encounters = "location",
            name = "spearow",
            order = 1,
            past_abilities = emptyList(),
            past_types = emptyList(),
            stats = listOf(
                Stat(50, 50),
                Stat(60, 70),
                Stat(70, 60)
            ),
            weight = 60
        )
    }
}
