package com.example.mad_movie_app.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mad_movie_app.models.MovieViewModel
import com.example.mad_movie_app.screens.AddMovieScreen
import com.example.mad_movie_app.screens.DetailScreen
import com.example.mad_movie_app.screens.FavoriteScreen
import com.example.mad_movie_app.screens.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("homescreen")
    object Detail : Screen("detailscreen/{movieId}")
    object Favorite : Screen("favoritescreen")
    object AddMovie : Screen("addmoviescreen")
}

@Composable
fun MyNavigation(viewModel: MovieViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            Screen.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            val selectedMovie = viewModel.movies.collectAsState().value.find { it.id == movieId }
            DetailScreen(
                navController = navController,
                movie = selectedMovie,
                viewModel = viewModel
            )
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen(navController = navController, viewModel = viewModel, onMovieClick = { movieId ->
                navController.navigate(Screen.Detail.route.replace("{movieId}", movieId))})
        }
        composable(Screen.AddMovie.route) {
            AddMovieScreen(navController = navController, viewModel = viewModel)
        }
    }
}