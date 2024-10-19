package com.example.pokemon_listing.presentation.pokemon_list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_listing.domain.interactor.GetPokemonsInteractor
import com.example.pokemon_listing.domain.interactor.PokemonListStatus
import com.example.pokemon_listing.presentation.mappers.transformToPokemonListDisplayModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonsInteractor: GetPokemonsInteractor
) : ViewModel() {

    private val _pokemonListUiState = MutableStateFlow<PokemonListUiState>(
        PokemonListUiState.Loading
    )
    val pokemonListUiState = _pokemonListUiState.asStateFlow()

    private var offset = 0
    private val limit = 10
    internal var isLoadingMore = false

    init {
        getPokemons()
    }

    fun getPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isLoadingMore) return@launch
            isLoadingMore = true

            val currentState = _pokemonListUiState.value
            val currentList = (currentState as? PokemonListUiState.Ready)?.pokemonListDisplayModel ?: emptyList()

            getPokemonsInteractor.getPokemons(limit = limit, offset = offset).let { result ->
                when (result) {
                    is PokemonListStatus.Success -> {
                        offset += limit
                        val newList = currentList + result.pokemons.map { it.transformToPokemonListDisplayModel() }
                        _pokemonListUiState.value =
                            PokemonListUiState.Ready(pokemonListDisplayModel = newList)
                    }
                    is PokemonListStatus.Error -> {
                        _pokemonListUiState.value =
                            PokemonListUiState.Error(message = result.message)
                    }
                }
            }

            isLoadingMore = false
        }
    }
}
