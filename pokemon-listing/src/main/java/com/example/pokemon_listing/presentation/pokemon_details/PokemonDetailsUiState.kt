package com.example.pokemon_listing.presentation.pokemon_details


sealed interface PokemonDetailsUiState {
    data object Loading : PokemonDetailsUiState
    data class Error(
        val message: String?
    ) : PokemonDetailsUiState
    data class Ready(
        val pokemonDetailsDisplayModel: PokemonDetailsDisplayModel
    ) : PokemonDetailsUiState
}

data class PokemonDetailsDisplayModel(
    val name: String,
    val height: Int,
    val weight: Int
)