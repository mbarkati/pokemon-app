package com.example.pokemon_listing.data.remote


sealed interface PokemonListResponse {
    data class Success(val pokemons: JsonPokemonResponse) : PokemonListResponse
    data class Failure(val message: String) : PokemonListResponse
}