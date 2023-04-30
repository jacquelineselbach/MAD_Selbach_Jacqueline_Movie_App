package com.example.mad_movie_app.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.models.*
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
fun MyNavigation(
    viewModel: MovieViewModel,
    addMovieScreenViewModel: AddMovieScreenViewModel,
    sharedFavoriteViewModel: SharedFavoriteViewModel,
    movieRepository: MovieRepository
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = viewModel,
                sharedFavoriteViewModel = sharedFavoriteViewModel
            )
        }
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
                sharedFavoriteViewModel = sharedFavoriteViewModel
            )
        }
        composable(Screen.Favorite.route) {
            // Pass the favoriteViewModel to the FavoriteScreen composable function
            FavoriteScreen(
                navController = navController,
                viewModel = sharedFavoriteViewModel,
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.route.replace("{movieId}", movieId))
                }
            )
        }
        composable(Screen.AddMovie.route) {
            // Pass the addMovieScreenViewModel to the AddMovieScreen composable function
            AddMovieScreen(navController = navController, viewModel = addMovieScreenViewModel)
        }
    }
}
