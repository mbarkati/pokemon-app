package com.example.pokemonapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemon_listing.presentation.pokemon_list.PokemonListScreen
import com.example.pokemon_listing.presentation.Screen
import com.example.pokemon_listing.presentation.pokemon_details.PokemonDetailsScreen
import com.example.pokemonapplication.ui.theme.PokemonApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
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
            }
        }
    }
}