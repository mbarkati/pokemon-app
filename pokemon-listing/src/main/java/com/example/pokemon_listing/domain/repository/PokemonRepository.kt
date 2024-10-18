package com.example.pokemon_listing.domain.repository

import com.example.pokemon_listing.data.remote.JsonPokemon

interface PokemonRepository {

    suspend fun getPokemons(limit: Int, offset: Int): List<JsonPokemon>

}