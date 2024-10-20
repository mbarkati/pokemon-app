package com.example.pokemon_listing.presentation.pokemon_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.pokemon_listing.common.Constants
import com.example.pokemon_listing.domain.interactor.GetPokemonDetailsInteractor
import com.example.pokemon_listing.domain.interactor.PokemonDetailsStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var getPokemonDetailsInteractor: GetPokemonDetailsInteractor
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: PokemonDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPokemonDetailsInteractor = mock(GetPokemonDetailsInteractor::class.java)

        savedStateHandle = SavedStateHandle().apply {
            set(Constants.PARAM_POKEMON_NAME, "Pikachu")
        }
        viewModel = PokemonDetailsViewModel(getPokemonDetailsInteractor, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }



    /*@Test
    fun `getPokemonDetails emits Ready state when interactor returns success`() = runBlocking {
        // Given
        val mockDetails = PokemonDetailsEntity(id = 1, name = "Pikachu", height = 4, weight = 6, imageUrl = "imageUrl")
        given(getPokemonDetailsInteractor.getPokemonDetails("Pikachu")).willReturn(
            PokemonDetailsStatus.Success(mockDetails)
        )

        // When
        viewModel.getPokemonDetails("Pikachu")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.pokemonDetailsUiState.value
        assert(state is PokemonDetailsUiState.Ready)
        val readyState = state as PokemonDetailsUiState.Ready
        assertEquals(
            readyState.pokemonDetailsDisplayModel, PokemonDetailsDisplayModel(
                id = 1, name = "Pikachu", height = 4, weight = 6, imageUrl = "imageUrl"
            )
        )
    }*/

    @Test
    fun `getPokemonDetails emits Error state when interactor returns failure`() = runBlocking {
        // Given
        given(getPokemonDetailsInteractor.getPokemonDetails("Pikachu")).willReturn(
            PokemonDetailsStatus.Error("Failed to fetch Pokémon details")
        )

        // When
        viewModel.getPokemonDetails("Pikachu")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.pokemonDetailsUiState.value
        assert(state is PokemonDetailsUiState.Error)
        val errorState = state as PokemonDetailsUiState.Error
        assertEquals(
            errorState.message, "Failed to fetch Pokémon details"
        )
    }
}
