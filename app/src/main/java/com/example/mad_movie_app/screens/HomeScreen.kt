package com.example.mad_movie_app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.mad_movie_app.components.MovieList
import com.example.mad_movie_app.data.MovieViewState
import com.example.mad_movie_app.models.MovieViewModel
import com.example.mad_movie_app.models.SharedFavoriteViewModel
import com.example.mad_movie_app.navigation.Screen
import com.example.mad_movie_app.navigation.TopBar

/**
 * Composable function that displays the Home screen of the app.
 *
 * @param navController The navigation controller used to navigate to other screens.
 * @param viewModel The [MovieViewModel] used to fetch and manage movies data.
 * @param sharedFavoriteViewModel The [SharedFavoriteViewModel] used to manage the favorite movies data.
 */

@Composable
fun HomeScreen(navController: NavController, viewModel: MovieViewModel, sharedFavoriteViewModel: SharedFavoriteViewModel) {

    // observe movies from the ViewModel and collectAsState

    val movies by viewModel.movies.collectAsState(emptyList())

    // transform movies to a list of MovieViewState objects
    // by passing movies list as the key for the remember() function,
    // movieViewStateObjects will only be recomputed if the movies list changes

    val movieViewStateObjects by remember(movies) {

        // derived state is used to update the UI whenever the list of movies changes

        derivedStateOf {
            if (movies.isNotEmpty()) {
                movies.map { movie ->
                    MovieViewState(movie, sharedFavoriteViewModel.isFavoriteMovie(movie.id))
                }
            } else {
                emptyList()
            }
        }
    }

    Column {

        // show top bar with the favorite and add movie buttons

        TopBar(
            onFavoriteClick = { navController.navigate(Screen.Favorite.route) },
            onAddClick = { navController.navigate(Screen.AddMovie.route) }
        )

        // show the list of movies

        MovieList(
            movieViewStates = movieViewStateObjects,
            onMovieClick = { movieId: String ->
                navController.navigate(Screen.Detail.route.replace("{movieId}", movieId))
            }
        ) { movieId: String ->

            // toggle the favorite status of the movie when the favorite button is clicked

            sharedFavoriteViewModel.toggleFavorite(movieId)
        }
    }
}
