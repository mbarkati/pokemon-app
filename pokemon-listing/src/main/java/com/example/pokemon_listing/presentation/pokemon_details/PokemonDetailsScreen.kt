package com.example.pokemon_listing.presentation.pokemon_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun PokemonDetailsScreen(
    viewModel: PokemonDetailsViewModel = hiltViewModel()
) {

    val pokemonDetailsUiState = viewModel.pokemonDetailsUiState.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        when (pokemonDetailsUiState) {

            is PokemonDetailsUiState.Ready -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = pokemonDetailsUiState.pokemonDetailsDisplayModel.name
                    )
                    Text(
                        text = pokemonDetailsUiState.pokemonDetailsDisplayModel.height.toString()
                    )
                    Text(
                        text = pokemonDetailsUiState.pokemonDetailsDisplayModel.weight.toString()
                    )
                }
            }

            is PokemonDetailsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is PokemonDetailsUiState.Error -> {
                Text(
                    text = "Error",
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