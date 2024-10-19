package com.example.pokemon_listing.data.remote

import com.google.gson.annotations.SerializedName

data class JsonPokemon(
    val name: String,
    val url: String,
)

data class JsonPokemonResponse(
    val results: List<JsonPokemon>
)

data class JsonPokemonDetails(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: JsonSprites
)

data class JsonSprites(
    @SerializedName("front_default")
    val frontDefault: String
)