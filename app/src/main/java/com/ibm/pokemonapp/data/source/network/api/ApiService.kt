package com.ibm.pokemonapp.data.source.network.api

import com.ibm.pokemonapp.data.source.network.response.NetworkResponse
import com.ibm.pokemonapp.data.source.network.response.PokemonListResponse
import com.ibm.pokemonapp.data.source.network.response.PokemonResponse
import com.ibm.pokemonapp.data.source.network.response.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): NetworkResponse<PokemonListResponse, Response.ErrorResponse>

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonDetails(
        @Path("pokemonName") pokemonName: String
    ): NetworkResponse<PokemonResponse, Response.ErrorResponse>
}