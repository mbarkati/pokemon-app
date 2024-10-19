package com.example.pokemon_listing.domain.repository

import com.example.pokemon_listing.data.remote.PokemonDetailsResponse
import com.example.pokemon_listing.data.remote.PokemonListResponse

interface PokemonRepository {

    suspend fun getPokemons(limit: Int, offset: Int): PokemonListResponse

    suspend fun getPokemonDetails(name: String): PokemonDetailsResponse

}