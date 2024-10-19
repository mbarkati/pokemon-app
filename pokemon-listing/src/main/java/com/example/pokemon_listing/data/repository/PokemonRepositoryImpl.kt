package com.example.pokemon_listing.data.repository

import android.net.http.HttpException
import com.example.pokemon_listing.common.Constants.HTTP_EXCEPTION_ERROR_MESSAGE
import com.example.pokemon_listing.common.Constants.IO_EXCEPTION_ERROR_MESSAGE
import com.example.pokemon_listing.data.remote.PokemonApi
import com.example.pokemon_listing.data.remote.PokemonDetailsResponse
import com.example.pokemon_listing.data.remote.PokemonListResponse
import com.example.pokemon_listing.domain.repository.PokemonRepository
import java.io.IOException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
) : PokemonRepository {

    override suspend fun getPokemons(limit: Int, offset: Int): PokemonListResponse {
        return try {
            PokemonListResponse.Success(
                pokemons = api.getPokemons(limit = limit, offset = offset)
            )
        } catch (e: HttpException) {
            PokemonListResponse.Failure(message = e.localizedMessage ?: HTTP_EXCEPTION_ERROR_MESSAGE)
        } catch (e: IOException) {
            PokemonListResponse.Failure(message = IO_EXCEPTION_ERROR_MESSAGE)
        }
    }

    override suspend fun getPokemonDetails(name: String): PokemonDetailsResponse {
        return try {
            PokemonDetailsResponse.Success(
                pokemonDetails = api.getPokemonDetails(name)
            )
        } catch (e: HttpException) {
            PokemonDetailsResponse.Failure(message = e.localizedMessage ?: HTTP_EXCEPTION_ERROR_MESSAGE)
        } catch (e: IOException) {
            PokemonDetailsResponse.Failure(message = IO_EXCEPTION_ERROR_MESSAGE)
        }
    }
}