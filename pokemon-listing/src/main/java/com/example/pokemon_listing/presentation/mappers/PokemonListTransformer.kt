package com.example.pokemon_listing.presentation.mappers


import com.example.pokemon_listing.domain.model.PokemonDetailsEntity
import com.example.pokemon_listing.domain.model.PokemonEntity
import com.example.pokemon_listing.presentation.pokemon_details.PokemonDetailsDisplayModel
import com.example.pokemon_listing.presentation.pokemon_list.PokemonListElementDisplayModel

fun PokemonEntity.transformToPokemonListDisplayModel(): PokemonListElementDisplayModel {
    return PokemonListElementDisplayModel(
        name = name,
        picture = picture
    )
}

fun PokemonDetailsEntity.transformToPokemonDetailsDisplayModel(): PokemonDetailsDisplayModel {
    return PokemonDetailsDisplayModel(
        name = name,
        height = height,
        weight = weight
    )
}