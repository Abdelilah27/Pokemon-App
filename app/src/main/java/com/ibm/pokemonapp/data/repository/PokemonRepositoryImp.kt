package com.ibm.pokemonapp.data.repository

import com.ibm.pokemonapp.data.source.network.api.ApiService
import com.ibm.pokemonapp.data.source.network.response.NetworkResponse
import com.ibm.pokemonapp.data.source.network.response.PokemonListResponse
import com.ibm.pokemonapp.data.source.network.response.PokemonResponse
import com.ibm.pokemonapp.domain.model.Resource
import com.ibm.pokemonapp.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImp @Inject constructor(private var apiService: ApiService) :
    PokemonRepository {

    override suspend fun getPokemonList(params: Pair<Int, Int>): Resource<PokemonListResponse> {
        try {
            return when (val response = apiService.getPokemonList(params.first, params.second)) {
                is NetworkResponse.Success -> {
                    Resource.Success(response.body!!)
                }

                is NetworkResponse.ApiError -> {
                    Resource.Error(response.code, "Failed to fetch Pokémon list")
                }

                is NetworkResponse.NetworkError -> {
                    Resource.Error(2000, "Network error")
                }

                else -> {
                    Resource.Error(1000, "Unexpected error during Pokémon list retrieval")
                }
            }
        } catch (e: Exception) {
            return Resource.Error(500, "Unexpected error")
        }
    }

    override suspend fun getPokemonDetails(pokemonName: String): Resource<PokemonResponse> {
        try {
            return when (val response = apiService.getPokemonDetails(pokemonName)) {
                is NetworkResponse.Success -> {
                    Resource.Success(response.body!!)
                }

                is NetworkResponse.ApiError -> {
                    Resource.Error(
                        response.code,
                        "Failed to fetch Pokémon details"
                    )
                }

                is NetworkResponse.NetworkError -> {
                    Resource.Error(2000, "Network error")
                }

                else -> {
                    Resource.Error(
                        1000,
                        "Unexpected error during Pokémon details retrieval"
                    )
                }
            }
        } catch (e: Exception) {
            return Resource.Error(500, "Unexpected error")
        }
    }
}
