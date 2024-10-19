package com.example.pokemon_listing.data.remote

data class JsonPokemon(
    val name: String,
    val url: String,
)

data class JsonPokemonResponse(
    val results: List<JsonPokemon>
)

data class JsonPokemonDetails(
    val name: String,
    val height: Int,
    val weight: Int,
)