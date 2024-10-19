package com.example.pokemon_listing.presentation.pokemon_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokemon_listing.presentation.pokemon_list.PokemonListElementDisplayModel
import java.util.Locale

@Composable
fun PokemonListItem(
    pokemon: PokemonListElementDisplayModel,
    onItemClick: (PokemonListElementDisplayModel) -> Unit
) {
    Text(
        text = pokemon.name.capitalize(Locale.ROOT),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick(pokemon) },
        textAlign = TextAlign.Start
    )
}