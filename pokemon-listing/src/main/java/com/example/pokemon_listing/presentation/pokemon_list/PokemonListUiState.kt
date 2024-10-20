package com.example.pokemon_listing.presentation.pokemon_list


sealed interface PokemonListUiState {
    data object Loading : PokemonListUiState
    data class Error(
        val message: String?
    ) : PokemonListUiState
    data class Ready(
        val pokemonListDisplayModel: List<PokemonListElementDisplayModel>
    ) : PokemonListUiState
}

data class PokemonListElementDisplayModel(
    val name: String
)