package com.example.pokemon_listing.presentation.pokemon_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemon_listing.presentation.Screen
import com.example.pokemon_listing.presentation.pokemon_list.components.PokemonListItem


@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

    val pokemonListUiState = viewModel.pokemonListUiState.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        when (pokemonListUiState) {
            is PokemonListUiState.Ready -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(pokemonListUiState.pokemonListDisplayModel.size) { index ->
                        val pokemon = pokemonListUiState.pokemonListDisplayModel[index]
                        PokemonListItem(
                            pokemon = pokemon,
                            onItemClick = {
                                navController.navigate(Screen.PokemonDetailScreen.route + "/${pokemon.name}")
                            }
                        )
                        // Trigger pagination when reaching the end
                        if (index == pokemonListUiState.pokemonListDisplayModel.lastIndex) {
                            viewModel.getPokemons()
                        }
                    }
                    item {
                        // Show a loading indicator when more data is loading
                        if (viewModel.isLoadingMore) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }

            is PokemonListUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is PokemonListUiState.Error -> {
                Text(
                    text = pokemonListUiState.message ?: "",
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}