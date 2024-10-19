package com.example.pokemon_listing.presentation.pokemon_details


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_listing.common.Constants
import com.example.pokemon_listing.domain.interactor.GetPokemonDetailsInteractor
import com.example.pokemon_listing.domain.interactor.PokemonDetailsStatus
import com.example.pokemon_listing.presentation.mappers.transformToPokemonDetailsDisplayModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val getPokemonDetailsInteractor: GetPokemonDetailsInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _pokemonDetailsUiState = MutableStateFlow<PokemonDetailsUiState>(
        PokemonDetailsUiState.Loading
    )
    val pokemonDetailsUiState = _pokemonDetailsUiState.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.PARAM_POKEMON_NAME)?.let { name ->
            getPokemonDetails(name = name)
        }
    }

    private fun getPokemonDetails(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getPokemonDetailsInteractor.getPokemonDetails(name).let { result ->
                when (result) {
                    is PokemonDetailsStatus.Success -> {
                        _pokemonDetailsUiState.value =
                            PokemonDetailsUiState.Ready(
                                pokemonDetailsDisplayModel = result.pokemonDetails.transformToPokemonDetailsDisplayModel()
                            )
                    }
                    is PokemonDetailsStatus.Error -> {
                        _pokemonDetailsUiState.value =
                            PokemonDetailsUiState.Error(message = result.message)
                    }
                }
            }
        }
    }
}
