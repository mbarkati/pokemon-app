package com.example.pokemon_listing.domain.interactor

import com.example.pokemon_listing.domain.model.PokemonEntity

sealed class PokemonListStatus {
    data class Success(val pokemons: List<PokemonEntity>) : PokemonListStatus()
    data class Error(val message: String) : PokemonListStatus()
}