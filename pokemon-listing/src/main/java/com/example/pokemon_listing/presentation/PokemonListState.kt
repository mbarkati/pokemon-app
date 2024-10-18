package com.example.pokemon_listing.presentation

import com.example.pokemon_listing.domain.model.PokemonEntity

data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemons: List<PokemonEntity> = emptyList(),
    val error: String = ""
)