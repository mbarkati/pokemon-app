package com.example.pokemon_listing.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onEach
import androidx.compose.runtime.State
import com.example.pokemon_listing.common.Resource
import com.example.pokemon_listing.domain.interactor.GetPokemonsInteractor
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonsInteractor: GetPokemonsInteractor
) : ViewModel() {

    private val _state = mutableStateOf(PokemonListState())
    val state: State<PokemonListState> = _state

    private var currentPage = 0
    private val pageSize = 2 // Taille des éléments par page
    private var isLoadingMore = false

    init {
        getPokemons()
    }

    fun getPokemons() {
        if (isLoadingMore) return // Évite de lancer plusieurs fois la pagination en même temps

        isLoadingMore = true
        getPokemonsInteractor(pageSize, currentPage * pageSize).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val newPokemons = result.data ?: emptyList()
                    _state.value = _state.value.copy(
                        pokemons = _state.value.pokemons + newPokemons, // Ajoute les nouvelles données à la liste existante
                        isLoading = false
                    )
                    currentPage++ // Incrémente la page après un chargement réussi
                    isLoadingMore = false
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occured",
                        isLoading = false
                    )
                    isLoadingMore = false
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}