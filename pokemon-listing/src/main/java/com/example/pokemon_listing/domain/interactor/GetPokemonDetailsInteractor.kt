package com.example.pokemon_listing.domain.interactor

import com.example.pokemon_listing.data.remote.PokemonDetailsResponse
import com.example.pokemon_listing.data.remote.toPokemonDetailsEntity
import com.example.pokemon_listing.domain.repository.PokemonRepository
import javax.inject.Inject


class GetPokemonDetailsInteractor @Inject constructor(
    private val repository: PokemonRepository
) {

    internal suspend fun getPokemonDetails(name: String): PokemonDetailsStatus {
        return when(val result = repository.getPokemonDetails(name = name)){
            is PokemonDetailsResponse.Success -> {
                PokemonDetailsStatus.Success (
                    pokemonDetails = result.pokemonDetails.toPokemonDetailsEntity()
                )
            }
            is PokemonDetailsResponse.Failure -> {
                PokemonDetailsStatus.Error(message = result.message)
            }
        }
    }
}