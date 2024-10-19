package com.example.pokemon_listing.domain.interactor

import com.example.pokemon_listing.domain.model.PokemonDetailsEntity

sealed class PokemonDetailsStatus {
    data class Success(val pokemonDetails: PokemonDetailsEntity) : PokemonDetailsStatus()
    data class Error(val message: String) : PokemonDetailsStatus()
}