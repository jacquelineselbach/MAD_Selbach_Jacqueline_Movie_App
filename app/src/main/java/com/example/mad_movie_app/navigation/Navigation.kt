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
 * Navigation composable function sets up the navigation for the application.
 *
 * @param viewModel The MovieViewModel instance used for the HomeScreen.
 * @param addMovieScreenViewModel The AddMovieScreenViewModel instance used for the AddMovieScreen.
 * @param sharedFavoriteViewModel The SharedFavoriteViewModel instance used for sharing favorites between screens.
 * @param movieRepository The MovieRepository instance used for fetching movie data.
 */

@Composable
fun Navigation(
    viewModel: MovieViewModel,
    addMovieScreenViewModel: AddMovieScreenViewModel,
    sharedFavoriteViewModel: SharedFavoriteViewModel,
    movieRepository: MovieRepository
) {

    // remember NavController for navigation between composables

    val navController = rememberNavController()

    // set up NavHost with different composable destinations

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        // Home screen composable
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = viewModel,
                sharedFavoriteViewModel = sharedFavoriteViewModel
            )
        }

        // detail screen composable with movieId argument

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

        // favorite screen composable

        composable(Screen.Favorite.route) {
            FavoriteScreen(
                navController = navController,
                viewModel = sharedFavoriteViewModel,
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.route.replace("{movieId}", movieId))
                }
            )
        }

        // add movie screen composable

        composable(Screen.AddMovie.route) {
            AddMovieScreen(navController = navController, viewModel = addMovieScreenViewModel)
        }
    }
}

// sealed classes for screens - restricts number of possible subclasses
sealed class Screen(val route: String) {

    // four subclasses: Home, Detail, Favorite, and AddMovie
    // subclasses are defined as object declarations, which means they are singletons
    object Home : Screen("homescreen")
    object Detail : Screen("detailscreen/{movieId}")
    object Favorite : Screen("favoritescreen")
    object AddMovie : Screen("addmoviescreen")
}
