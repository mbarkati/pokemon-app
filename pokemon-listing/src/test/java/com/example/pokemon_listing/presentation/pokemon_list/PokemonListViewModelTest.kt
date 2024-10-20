package com.example.pokemon_listing.presentation.pokemon_list


import com.example.pokemon_listing.domain.interactor.GetPokemonsInteractor
import com.example.pokemon_listing.domain.interactor.PokemonListStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {


    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var getPokemonsInteractor: GetPokemonsInteractor
    private lateinit var viewModel: PokemonListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPokemonsInteractor = mock(GetPokemonsInteractor::class.java)
        viewModel = PokemonListViewModel(getPokemonsInteractor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    /*@Test
    fun `getPokemons should update UI state to Ready when interactor returns Success`() = runBlocking {
        // Given
        val mockPokemonList = listOf(
            PokemonListElementDisplayModel(name = "Pikachu"),
            PokemonListElementDisplayModel(name = "Bulbasaur")
        )
        val mockStatus = PokemonListStatus.Success(
            pokemons = listOf(
                PokemonEntity(name = "Pikachu", url = "https://pokeapi.co/pikachu"),
                PokemonEntity(name = "Bulbasaur", url = "https://pokeapi.co/bulbasaur")
            )
        )
        given(getPokemonsInteractor.getPokemons(limit = 10, offset = 0)).willReturn(mockStatus)

        // When
        viewModel.getPokemons()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.pokemonListUiState.value
        assert(currentState is PokemonListUiState.Ready)
        val readyState = viewModel.pokemonListUiState.value as PokemonListUiState.Ready
        assert(mockPokemonList == readyState.pokemonListDisplayModel)
    }*/

    @Test
    fun `getPokemons should update UI state to Error when API call fails`() = runBlocking {
        // Given
        val errorMessage = "Failed to fetch pokemons"
        given(getPokemonsInteractor.getPokemons(limit = 10, offset = 0)).willReturn(
            PokemonListStatus.Error(message = errorMessage)

        )

        // When
        viewModel.getPokemons()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.pokemonListUiState.value
        assert(currentState is PokemonListUiState.Error) { "Expected UI state to be Error" }
        val errorState = currentState as PokemonListUiState.Error
        assert(errorState.message == errorMessage) { "Expected error message to match" }
    }
}