package com.example.mad_movie_app.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.mad_movie_app.data.MovieViewState

/**
 * A composable function that displays the list of movie cards.
 *
 * @param movieViewStates A list of MovieViewState objects representing each movie card's state.
 * @param onMovieClick A lambda function to be executed when a movie card is clicked, with the movie ID as its parameter.
 * @param onFavoriteClick A lambda function to be executed when the favorite button is clicked, with the movie ID as its parameter.
 */

@Composable
fun MovieList(
    movieViewStates: List<MovieViewState>,
    onMovieClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    // create LazyColumn to display a list of movie cards

    LazyColumn {
        items(movieViewStates) { movieViewState ->

            // remember the expanded state for each movie card

            val isExpanded = remember { mutableStateOf(false) }

            println("Movie: ${movieViewState.movie}")

            // create MovieCard for each movie in the list

            MovieCard(
                movie = movieViewState.movie,
                isFavorite = movieViewState.isFavorite,
                onMovieClick = { onMovieClick(movieViewState.movie.id) },
                onFavoriteClick = {
                    onFavoriteClick(movieViewState.movie.id)
                },
                isExpanded = isExpanded
            ) { isExpanded.value = !isExpanded.value }
        }
    }
}
