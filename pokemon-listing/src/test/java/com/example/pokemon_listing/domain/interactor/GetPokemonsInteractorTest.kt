package com.example.pokemon_listing.domain.interactor

import com.example.pokemon_listing.data.remote.JsonPokemon
import com.example.pokemon_listing.data.remote.JsonPokemonResponse
import com.example.pokemon_listing.data.remote.PokemonListResponse
import com.example.pokemon_listing.domain.model.PokemonEntity
import com.example.pokemon_listing.domain.repository.PokemonRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetPokemonsInteractorTest {

    @Mock
    private lateinit var mockRepository: PokemonRepository

    private lateinit var getPokemonsInteractor: GetPokemonsInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getPokemonsInteractor = GetPokemonsInteractor(mockRepository)
    }

    @Test
    fun `getPokemons should return Success when repository returns success`() = runBlocking {
        // Given
        val limit = 10
        val offset = 0
        val mockPokemons = JsonPokemonResponse(
            results = listOf(
                JsonPokemon(name = "Pikachu", url = "https://pokeapi.co/pikachu"),
                JsonPokemon(name = "Bulbasaur", url = "https://pokeapi.co/bulbasaur")
            )
        )

        val expectedEntities = listOf(
            PokemonEntity(name = "Pikachu", url = "https://pokeapi.co/pikachu"),
            PokemonEntity(name = "Bulbasaur", url = "https://pokeapi.co/bulbasaur")
        )
        given(mockRepository.getPokemons(limit, offset)).willReturn(
            PokemonListResponse.Success(mockPokemons)

        )

        // When
        val result = getPokemonsInteractor.getPokemons(limit, offset)

        // Then
        assertEquals(PokemonListStatus.Success(expectedEntities), result)
    }

    @Test
    fun `getPokemons should return Error when repository returns failure`() = runBlocking {
        // Given
        val limit = 10
        val offset = 0
        val errorMessage = "Failed to fetch pokemons"
        given(mockRepository.getPokemons(limit, offset)).willReturn(
            PokemonListResponse.Failure(errorMessage)

        )

        // When
        val result = getPokemonsInteractor.getPokemons(limit, offset)

        // Then
        assertEquals(PokemonListStatus.Error(message = errorMessage), result)
    }
}
