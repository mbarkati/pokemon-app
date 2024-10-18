package com.example.pokemon_listing.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): JsonPokemonResponse

}