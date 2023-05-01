package com.example.mad_movie_app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mad_movie_app.components.MovieCard
import com.example.mad_movie_app.models.SharedFavoriteViewModel
import com.example.mad_movie_app.navigation.SimpleAppBar

/**
 * Composable function displaying the Favorite Screen and the user's favorite movies.
 *
 * @param navController The navigation controller used for navigating to other screens.
 * @param viewModel The [SharedFavoriteViewModel] used to manage the favorite movies data.
 * @param onMovieClick The callback function to execute when a movie is clicked.
 */

@Composable
fun FavoriteScreen(navController: NavHostController, viewModel: SharedFavoriteViewModel, onMovieClick: (String) -> Unit) {

    // observe the favorite movies from the ViewModel

    val favoriteMovies = viewModel.favoriteMovies.collectAsState()

    // remember the LazyListState for the LazyColumn

    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SimpleAppBar(
            title = "Favorite Movies",
            onBackClick = { navController.popBackStack() }
        )

        // if the list of favorite movies is empty, display a message to the user

        if (favoriteMovies.value.isEmpty()) {
            Text(
                text = "You have not added any favorite movies yet!",
                modifier = Modifier.padding(16.dp)
            )
        } else {

            // otherwise, display the list of favorite movies in a LazyColumn
            // LazyColumn only composes the visible items on the screen

            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(favoriteMovies.value.toList(), key = { movie -> movie.id }) { movie ->

                    // remember whether the MovieCard is expanded or not

                    val isExpanded = remember { mutableStateOf(false) }

                    MovieCard(
                        movie = movie,
                        isFavorite = viewModel.isFavoriteMovie(movie.id),
                        onMovieClick = { onMovieClick(movie.id) },
                        onFavoriteClick = {
                            viewModel.toggleFavorite(movie.id)
                        },
                        isExpanded = isExpanded,
                        onExpandClick = { isExpanded.value = !isExpanded.value }
                    )

                }
            }
        }
    }
}
