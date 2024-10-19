package com.example.pokemon_listing.domain.model

data class PokemonDetailsEntity(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String
)