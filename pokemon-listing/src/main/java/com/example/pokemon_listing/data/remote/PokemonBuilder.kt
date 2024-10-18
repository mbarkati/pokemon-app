package com.example.pokemon_listing.data.remote

import com.example.pokemon_listing.domain.model.PokemonEntity


fun JsonPokemon.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        picture = buildPicture(name, url)
    )
}

private fun buildPicture( name: String, url: String): String {
    return name + url
}