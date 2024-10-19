package com.example.pokemon_listing.data.remote

import com.example.pokemon_listing.domain.model.PokemonDetailsEntity
import com.example.pokemon_listing.domain.model.PokemonEntity


fun JsonPokemon.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        url = url
    )
}

fun JsonPokemonDetails.toPokemonDetailsEntity(): PokemonDetailsEntity {
    return PokemonDetailsEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        imageUrl = sprites.frontDefault
    )
}