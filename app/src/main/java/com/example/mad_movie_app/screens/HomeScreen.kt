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

@Composable
fun HomeScreen(navController: NavController, viewModel: MovieViewModel, sharedFavoriteViewModel: SharedFavoriteViewModel) {
    // Observe the movies from the ViewModel
    val movies by viewModel.movies.collectAsState(emptyList())

    val movieViewStates by remember(movies) {
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
        TopBar(
            onFavoriteClick = { navController.navigate(Screen.Favorite.route) },
            onAddClick = { navController.navigate(Screen.AddMovie.route) }
        )
        MovieList(
            movieViewStates = movieViewStates,
            onMovieClick = { movieId: String ->
                navController.navigate(Screen.Detail.route.replace("{movieId}", movieId))
            }
        ) { movieId: String ->
            sharedFavoriteViewModel.toggleFavorite(movieId)
        }
    }
}
