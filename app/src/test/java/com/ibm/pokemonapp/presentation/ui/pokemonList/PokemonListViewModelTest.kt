package com.ibm.pokemonapp.presentation.ui.pokemonList

import com.ibm.pokemonapp.data.source.network.response.PokemonListResponse
import com.ibm.pokemonapp.data.source.network.response.model.Result
import com.ibm.pokemonapp.domain.model.Resource
import com.ibm.pokemonapp.domain.usecase.GetPokemonListUseCase
import com.ibm.pokemonapp.presentation.utils.MainDispatcherRule
import com.ibm.pokemonapp.presentation.utils.runTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PokemonListViewModel

    @Before
    fun setup() {
    }

    @Test
    fun `getPokemonList should update viewModel properties on success`() = runTest {
        // Arrange
        val mockResponse = createMockResponse(listOf("spearow", "fearow", "ekans"))
        val getPokemonListUseCase = mockk<GetPokemonListUseCase>(relaxed = true)
        coEvery { getPokemonListUseCase.invoke(any()) } returns flowOf(Resource.Success(mockResponse))

        // Act
        viewModel = PokemonListViewModel(getPokemonListUseCase)

        // Assert
        with(viewModel) {
            assertPokemonListProperties(
                mockResponse.results.size,
                isEmpty = false,
                errorMessage = "",
                errorId = 0
            )
        }
    }

    @Test
    fun `getPokemonList should handle error response`() = runTest {
        // Arrange
        val getPokemonListUseCase = mockk<GetPokemonListUseCase>(relaxed = true)
        coEvery { getPokemonListUseCase.invoke(any()) } returns flowOf(
            Resource.Error(
                500, "An error occurred"
            )
        )

        // Act
        viewModel = PokemonListViewModel(getPokemonListUseCase)

        // Assert
        with(viewModel) {
            assertPokemonListProperties(
                size = 0,
                isEmpty = true,
                errorMessage = "An error occurred",
                errorId = 500
            )
        }
    }

    @Test
    fun `getPokemonList should handle exception and update viewModel properties`() = runTest {
        // Arrange
        val getPokemonListUseCase = mockk<GetPokemonListUseCase>(relaxed = true)
        coEvery { getPokemonListUseCase.invoke(any()) } throws Exception("An unexpected error")

        // Act
        viewModel = PokemonListViewModel(getPokemonListUseCase)

        // Assert
        with(viewModel) {
            assertPokemonListProperties(
                size = 0,
                isEmpty = true,
                errorMessage = "An error occurred",
                errorId = 999
            )
        }
    }


    // Helper function to create a PokemonListResponse
    private fun createMockResponse(names: List<String>): PokemonListResponse {
        return PokemonListResponse(
            20, "Next", "Previous", names.mapIndexed { index, name ->
                Result(name, "https://pokeapi.co/api/v2/pokemon/${index + 1}/")
            }
        )
    }


    // Helper function to assert PokemonList properties
    private fun PokemonListViewModel.assertPokemonListProperties(
        size: Int,
        isEmpty: Boolean,
        errorMessage: String,
        errorId: Int?,
    ) {
        Assert.assertEquals(size, pokemonList.value.size)
        Assert.assertEquals(isEmpty, pokemonList.value.isEmpty())
        Assert.assertEquals(errorMessage, workflowError.value.message)
        Assert.assertEquals(errorId, workflowError.value.id)
    }

}
