package com.example.pokemon_listing.domain.interactor

import com.example.pokemon_listing.data.remote.PokemonListResponse
import com.example.pokemon_listing.data.remote.toPokemonEntity
import com.example.pokemon_listing.domain.repository.PokemonRepository
import javax.inject.Inject


class GetPokemonsInteractor @Inject constructor(
    private val repository: PokemonRepository
) {

    companion object {
        const val OFFSET_PAGINATION_START = 0
        const val LIMIT_PAGINATION_START = 20
    }

    private var offset: Int = OFFSET_PAGINATION_START
    private val limit: Int = LIMIT_PAGINATION_START

    suspend fun getPokemons(): PokemonListStatus {

        return when(val result = repository.getPokemons(limit = limit, offset = offset)) {
            is PokemonListResponse.Success -> {
                offset += limit
                PokemonListStatus.Success(pokemons = result.pokemons.results.map { it.toPokemonEntity() })
            }
            is PokemonListResponse.Failure -> {
                PokemonListStatus.Error(message = result.message)
            }
        }
    }
}