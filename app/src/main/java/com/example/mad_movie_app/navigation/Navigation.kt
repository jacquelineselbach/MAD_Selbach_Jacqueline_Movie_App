package com.example.mad_movie_app.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mad_movie_app.components.Movie
import com.example.mad_movie_app.components.getMovies
import com.example.mad_movie_app.screens.DetailScreen
import com.example.mad_movie_app.screens.FavoriteScreen
import com.example.mad_movie_app.screens.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("homescreen")
    object Detail : Screen("detailscreen/{movieId}")
    object Favorite : Screen("favoritescreen")
}

fun getMovieById(id: String?): Movie? {
    val movies = getMovies()
    return movies.find { it.id == id }
}

@Composable
fun MyNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            Screen.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val selectedMovie = getMovieById(backStackEntry.arguments?.getString("movieId"))
            DetailScreen(
                navController = navController,
                movie = selectedMovie
            ) {}
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen(navController = navController)
        }
    }
}