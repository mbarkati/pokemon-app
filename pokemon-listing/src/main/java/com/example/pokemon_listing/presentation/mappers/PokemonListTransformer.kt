package com.example.pokemon_listing.presentation.mappers


import com.example.pokemon_listing.domain.model.PokemonEntity
import com.example.pokemon_listing.presentation.PokemonListElementDisplayModel

fun PokemonEntity.transformToPokemonListDisplayModel(): PokemonListElementDisplayModel {
    return PokemonListElementDisplayModel(
        name = name,
        picture = picture
    )
}