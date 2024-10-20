package com.example.pokemon_listing.domain.interactor

import com.example.pokemon_listing.domain.repository.PokemonRepository
import com.example.pokemon_listing.data.remote.PokemonListResponse
import com.example.pokemon_listing.data.remote.JsonPokemon
import com.example.pokemon_listing.data.remote.JsonPokemonResponse
import com.example.pokemon_listing.domain.model.PokemonEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetPokemonsInteractorTest {

    @Mock
    private lateinit var repository: PokemonRepository

    private lateinit var interactor: GetPokemonsInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        interactor = GetPokemonsInteractor(repository)
    }

    @Test
    fun `getPokemons should return Success when repository returns success`(): Unit = runBlocking {
        // Given
        val jsonPokemon = JsonPokemon(name = "bulbasaur", url = "url")
        val jsonPokemonResponse = JsonPokemonResponse(results = listOf(jsonPokemon))
        val pokemonListResponse = PokemonListResponse.Success(pokemons = jsonPokemonResponse)
        val expectedEntity = listOf(PokemonEntity(name = "bulbasaur", url = "url"))

        given(repository.getPokemons(limit = 20, offset = 0)).willReturn(pokemonListResponse)

        // When
        val result = interactor.getPokemons()

        // Then
        assertTrue(result is PokemonListStatus.Success)
        assertEquals(PokemonListStatus.Success(expectedEntity), result)
    }

    @Test
    fun `getPokemons should return Error when repository returns failure`() = runBlocking {
        // Given
        val errorMessage = "Error fetching data"
        val pokemonListResponse = PokemonListResponse.Failure(message = errorMessage)
        given(repository.getPokemons(limit = 20, offset = 0)).willReturn(pokemonListResponse)

        // When
        val result = interactor.getPokemons()

        // Then
        assertTrue(result is PokemonListStatus.Error)
        val errorResult = result as PokemonListStatus.Error
        assertEquals(errorMessage, errorResult.message)
    }
}
