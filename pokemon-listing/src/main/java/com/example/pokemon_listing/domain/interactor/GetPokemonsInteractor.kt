package com.example.pokemon_listing.domain.interactor

import com.example.pokemon_listing.data.remote.PokemonListResponse
import com.example.pokemon_listing.data.remote.toPokemonEntity
import com.example.pokemon_listing.domain.repository.PokemonRepository
import javax.inject.Inject


class GetPokemonsInteractor @Inject constructor(
    private val repository: PokemonRepository
) {

    internal suspend fun getPokemons(limit: Int, offset: Int): PokemonListStatus {
        return when(val result = repository.getPokemons(limit =limit, offset = offset)){
            is PokemonListResponse.Success -> {
                PokemonListStatus.Success (
                    pokemons = result.pokemons.results.map { it.toPokemonEntity() }
                )
            }
            is PokemonListResponse.Failure -> {
                PokemonListStatus.Error(message = result.message)
            }
        }
    }
}