package com.example.pokemon_listing.data.remote


sealed interface PokemonDetailsResponse {
    data class Success(val pokemonDetails: JsonPokemonDetails) : PokemonDetailsResponse
    data class Failure(val message: String) : PokemonDetailsResponse
}