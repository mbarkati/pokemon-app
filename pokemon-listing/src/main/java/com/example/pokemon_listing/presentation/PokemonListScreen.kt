package com.example.pokemon_listing.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemon_listing.presentation.components.PokemonListItem


@Composable
fun PokemonListScreen(
    //navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(state.pokemons.size) { index ->
                val Pokemon = state.pokemons[index]
                PokemonListItem(
                    pokemon = Pokemon,
                    onItemClick = {
                        //navController.navigate(Screen.PokemonDetailScreen.route + "/${Pokemon.id}")
                    }
                )

                // Vérifie si l'utilisateur a atteint le bas de la liste
                if (index >= state.pokemons.size - 1 && !state.isLoading) {
                    // Appelle le ViewModel pour charger plus de données
                    viewModel.getPokemons()
                }
            }
        }

        if(state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}