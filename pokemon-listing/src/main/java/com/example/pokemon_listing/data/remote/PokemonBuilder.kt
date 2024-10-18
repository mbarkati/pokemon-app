package com.example.pokemon_listing.data.remote

import com.example.pokemon_listing.domain.model.PokemonEntity


fun JsonPokemon.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        picture = buildImageUrl(url)
    )
}

private fun buildImageUrl(url: String): String {
    val pokemonId = url.split("/").filter { it.isNotEmpty() }.last()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
}