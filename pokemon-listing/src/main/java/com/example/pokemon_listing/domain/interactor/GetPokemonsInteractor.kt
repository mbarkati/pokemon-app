package com.example.pokemon_listing.domain.interactor

import android.net.http.HttpException
import com.example.pokemon_listing.common.Resource
import com.example.pokemon_listing.data.remote.toPokemonEntity
import com.example.pokemon_listing.domain.model.PokemonEntity
import com.example.pokemon_listing.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject


class GetPokemonsInteractor @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(limit: Int, offset: Int): Flow<Resource<List<PokemonEntity>>> = flow {
        try {
            emit(Resource.Loading<List<PokemonEntity>>())
            val pokemons = repository.getPokemons(limit = limit, offset = offset).map { it.toPokemonEntity() }
            emit(Resource.Success<List<PokemonEntity>>(pokemons))
        } catch(e: HttpException) {
            emit(Resource.Error<List<PokemonEntity>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<PokemonEntity>>("Couldn't reach server. Check your internet connection."))
        }
    }
}