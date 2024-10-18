package com.example.pokemon_listing.data.repository

import com.example.pokemon_listing.data.remote.JsonPokemon
import com.example.pokemon_listing.data.remote.PokemonApi
import com.example.pokemon_listing.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
) : PokemonRepository {

    override suspend fun getPokemons(limit: Int, offset: Int): List<JsonPokemon> {
        return api.getPokemons(limit = limit, offset = offset).results
    }

}