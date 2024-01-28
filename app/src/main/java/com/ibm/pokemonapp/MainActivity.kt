package com.ibm.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ibm.pokemonapp.presentation.ui.onBoarding.OnBoardingScreen
import com.ibm.pokemonapp.presentation.ui.pokemonDetails.PokemonDetailsScreen
import com.ibm.pokemonapp.presentation.ui.pokemonList.PokemonListScreen
import com.ibm.pokemonapp.presentation.ui.theme.PokemonAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }
        setContent {
            PokemonAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "onboarding_screen") {
                    composable("onboarding_screen") {
                        OnBoardingScreen(navController = navController)
                    }
                    composable("pokemon_list_screen") {
                        PokemonListScreen(navController = navController)
                    }
                    composable(
                        "pokemon_details_screen/{pokemonColor}/{pokemonName}", arguments = listOf(
                            navArgument("pokemonColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )) {
                        val pokemonColor = remember {
                            val color = it.arguments?.getInt("pokemonColor")
                            color?.let { Color(it) } ?: Color.Black
                        }
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                        PokemonDetailsScreen(
                            navController = navController,
                            pokemonColor,
                            pokemonName
                        )
                    }
                }
            }
        }
    }
}
