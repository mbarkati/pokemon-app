package com.example.pokemon_listing.presentation.pokemon_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_listing.domain.interactor.GetPokemonsInteractor
import com.example.pokemon_listing.domain.interactor.PokemonListStatus
import com.example.pokemon_listing.presentation.mappers.transformToPokemonListDisplayModel
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

    var isLoadingMore by mutableStateOf(false)


    init {
        getPokemons()
    }

    fun getPokemons() {
        viewModelScope.launch {
            when(val result = getPokemonsInteractor.getPokemons()) {
                is PokemonListStatus.Success -> {
                    val updatedList = result.pokemons.map { it.transformToPokemonListDisplayModel() }

                    _pokemonListUiState.value =
                        PokemonListUiState.Ready(
                            pokemonListDisplayModel = (pokemonListUiState.value as? PokemonListUiState.Ready)?.pokemonListDisplayModel?.plus(updatedList) ?: updatedList
                        )
                    isLoadingMore = false

                }
                is PokemonListStatus.Error -> {
                    _pokemonListUiState.value = PokemonListUiState.Error(message = result.message)
                    isLoadingMore = false
                }
            }
        }
    }
}
