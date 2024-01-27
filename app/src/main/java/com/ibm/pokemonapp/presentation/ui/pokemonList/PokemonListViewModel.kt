package com.ibm.pokemonapp.presentation.ui.pokemonList

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.ibm.pokemonapp.data.models.PokemonListEntry
import com.ibm.pokemonapp.data.source.network.response.PokemonListResponse
import com.ibm.pokemonapp.data.source.network.response.Response
import com.ibm.pokemonapp.domain.model.Resource
import com.ibm.pokemonapp.domain.usecase.GetPokemonListUseCase
import com.ibm.pokemonapp.utils.Consts.DREAM_WORLD_IMAGES_URL
import com.ibm.pokemonapp.utils.Consts.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
) :
    ViewModel() {
    private var currentPage = 0
    var pokemonList = mutableStateOf<List<PokemonListEntry>>(listOf())
    var isEndReached = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var workflowError = mutableStateOf(
        Response.ErrorResponse(
            0,
            ""
        )
    )

    init {
        getPokemonList()
    }

    fun getPokemonList() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val param = Pair(
                    PAGE_SIZE, // 20
                    PAGE_SIZE * currentPage   // 20 * 1
                ) // Provide appropriate values for your use case // TODO
                getPokemonListUseCase.invoke(param).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            isEndReached.value =
                                currentPage * PAGE_SIZE >= result.data!!.count // 1302
                            // Parse the Url to get the image for each item
                            val pokemonListEntry = createPokemonListEntries(result.data)
                            currentPage++
                            isLoading.value = false
                            pokemonList.value += pokemonListEntry
                        }

                        is Resource.Error -> {
                            handleErrorResponse(result)
                        }

                        else -> {
                            handleErrorResponse(result)
                        }
                    }
                }
            } catch (e: Exception) {
                isLoading.value = false
                workflowError.value =
                    Response.ErrorResponse(
                        message = "An error occurred",// TODO
                        id = 999
                    )
            }
        }
    }

    private fun createPokemonListEntries(data: PokemonListResponse): List<PokemonListEntry> {
        val pokemonListEntry = data.results.mapIndexed { index, entry ->
            val number = if (entry.url.endsWith("/")) {
                entry.url.dropLast(1).takeLastWhile { it.isDigit() }
            } else {
                entry.url.takeLastWhile { it.isDigit() }
            }
            val url =
                "$DREAM_WORLD_IMAGES_URL${number}.svg"
            PokemonListEntry(
                entry.name.capitalize(Locale.ROOT),
                url,
                number.toInt()
            )
        }
        return pokemonListEntry
    }

    private fun handleErrorResponse(result: Resource<PokemonListResponse>) {
        isLoading.value = false
        workflowError.value = Response.ErrorResponse(
            message = result.message!!,
            id = result.code
        )
    }

    fun generatePokemonColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { color ->
                onFinish(Color(color))
            }
        }
    }

}