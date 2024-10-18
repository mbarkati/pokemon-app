package com.example.pokemon_listing.data.repository

import android.net.http.HttpException
import com.example.pokemon_listing.data.remote.PokemonApi
import com.example.pokemon_listing.data.remote.PokemonListResponse
import com.example.pokemon_listing.data.remote.toPokemonEntity
import com.example.pokemon_listing.domain.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            PokemonListResponse.Failure(message = e.message ?: "")
        } catch (e: IOException) {
            PokemonListResponse.Failure(message = e.message ?: "")
        }
    }
}