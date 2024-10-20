package com.example.pokemonapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.pokemon_listing.presentation.Screen
import com.example.pokemon_listing.presentation.pokemon_details.PokemonDetailsScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokemon_listing.presentation.pokemon_list.PokemonListScreen


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.PokemonListScreen.route
    ) {
        composable(
            route = Screen.PokemonListScreen.route
        ) {
            PokemonListScreen(navController)
        }
        composable(
            route = Screen.PokemonDetailScreen.route + "/{pokemonName}"
        ) {
            PokemonDetailsScreen()
        }
    }
}