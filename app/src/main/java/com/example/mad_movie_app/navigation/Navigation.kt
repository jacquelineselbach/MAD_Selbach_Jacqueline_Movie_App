package com.example.mad_movie_app.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.models.*
import com.example.mad_movie_app.screens.AddMovieScreen
import com.example.mad_movie_app.screens.DetailScreen
import com.example.mad_movie_app.screens.FavoriteScreen
import com.example.mad_movie_app.screens.HomeScreen

/**
 * MyNavigation composable function sets up the navigation for the application.
 *
 * @param viewModel The MovieViewModel instance used for the HomeScreen.
 * @param addMovieScreenViewModel The AddMovieScreenViewModel instance used for the AddMovieScreen.
 * @param sharedFavoriteViewModel The SharedFavoriteViewModel instance used for sharing favorites between screens.
 * @param movieRepository The MovieRepository instance used for fetching movie data.
 */

@Composable
fun MyNavigation(
    viewModel: MovieViewModel,
    addMovieScreenViewModel: AddMovieScreenViewModel,
    sharedFavoriteViewModel: SharedFavoriteViewModel,
    movieRepository: MovieRepository
) {
    // Remember the NavController for navigation between composables
    val navController = rememberNavController()

    // Set up the NavHost with different composable destinations
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        // Home screen composable
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = viewModel,
                sharedFavoriteViewModel = sharedFavoriteViewModel
            )
        }
        // Detail screen composable with movieId argument
        composable(
            Screen.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            val movie = movieRepository.getMovieById(movieId = movieId!!).collectAsState(initial = null).value
            val detailScreenViewModel = DetailScreenViewModel(movieRepository, movieId, sharedFavoriteViewModel)
            DetailScreen(
                navController = navController,
                movie = movie,
                detailScreenViewModel = detailScreenViewModel,
                viewModel = sharedFavoriteViewModel
            )
        }
        // Favorite screen composable
        composable(Screen.Favorite.route) {
            FavoriteScreen(
                navController = navController,
                viewModel = sharedFavoriteViewModel,
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.route.replace("{movieId}", movieId))
                }
            )
        }
        // Add movie screen composable
        composable(Screen.AddMovie.route) {
            AddMovieScreen(navController = navController, viewModel = addMovieScreenViewModel)
        }
    }
}

// Screen sealed class representing different screens in the application
sealed class Screen(val route: String) {
    object Home : Screen("homescreen")
    object Detail : Screen("detailscreen/{movieId}")
    object Favorite : Screen("favoritescreen")
    object AddMovie : Screen("addmoviescreen")
}
