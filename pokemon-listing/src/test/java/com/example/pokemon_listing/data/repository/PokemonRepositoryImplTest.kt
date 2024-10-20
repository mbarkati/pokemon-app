package com.example.pokemon_listing.data.repository

import com.example.pokemon_listing.common.Constants.IO_EXCEPTION_ERROR_MESSAGE
import com.example.pokemon_listing.data.remote.JsonPokemon
import com.example.pokemon_listing.data.remote.JsonPokemonDetails
import com.example.pokemon_listing.data.remote.JsonPokemonResponse
import com.example.pokemon_listing.data.remote.JsonSprites
import com.example.pokemon_listing.data.remote.PokemonApi
import com.example.pokemon_listing.data.remote.PokemonDetailsResponse
import com.example.pokemon_listing.data.remote.PokemonListResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.io.IOException


@ExperimentalCoroutinesApi
class PokemonRepositoryImplTest {

    private lateinit var repository: PokemonRepositoryImpl
    private lateinit var api: PokemonApi

    @Before
    fun setUp() {
        api = mock(PokemonApi::class.java)
        repository = PokemonRepositoryImpl(api)
    }

    @Test
    fun `getPokemons returns Success when API call is successful`() = runBlockingTest {
        // Given
        val limit = 20
        val offset = 0
        val response = JsonPokemonResponse(listOf(JsonPokemon(name = "Pikachu", url = "/pokemon/pikachu")))
        given(api.getPokemons(limit, offset)).willReturn(response)

        // When
        val result = repository.getPokemons(limit, offset)

        // Then
        assert(result is PokemonListResponse.Success)
        assert((result as PokemonListResponse.Success).pokemons == response)
        verify(api).getPokemons(limit, offset)
    }

    @Test
    fun `getPokemons returns Failure when IOException is thrown`() = runBlockingTest {
        // Given
        val limit = 20
        val offset = 0
        given(api.getPokemons(limit, offset)).willAnswer { throw IOException() }

        // When
        val result = repository.getPokemons(limit, offset)

        // Then
        assert(result is PokemonListResponse.Failure)
        assert((result as PokemonListResponse.Failure).message == IO_EXCEPTION_ERROR_MESSAGE)
        verify(api).getPokemons(limit, offset)
    }

    @Test
    fun `getPokemonDetails returns Success when API call is successful`() = runBlockingTest {
        // Given
        val name = "pikachu"
        val response = JsonPokemonDetails(id = 1, name = "Pikachu", height = 4, weight = 60, sprites = JsonSprites(frontDefault = "/sprites/pikachu.png"))
        given(api.getPokemonDetails(name)).willReturn(response)

        // When
        val result = repository.getPokemonDetails(name)

        // Then
        assert(result is PokemonDetailsResponse.Success)
        assert((result as PokemonDetailsResponse.Success).pokemonDetails == response)
        verify(api).getPokemonDetails(name)
    }

    @Test
    fun `getPokemonDetails returns Failure when IOException is thrown`() = runBlockingTest {
        // Given
        val name = "pikachu"
        given(api.getPokemonDetails(name)).willAnswer { throw IOException() }

        // When
        val result = repository.getPokemonDetails(name)

        // Then
        assert(result is PokemonDetailsResponse.Failure)
        assert((result as PokemonDetailsResponse.Failure).message == IO_EXCEPTION_ERROR_MESSAGE)
        verify(api).getPokemonDetails(name)
    }
}