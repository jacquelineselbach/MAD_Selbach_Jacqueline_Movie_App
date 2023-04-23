package com.example.mad_movie_app.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.mad_movie_app.data.MovieViewState

@Composable
fun MovieList(
    movieViewStates: List<MovieViewState>,
    onMovieClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    LazyColumn {
        items(movieViewStates) { movieViewState ->
            val isExpanded = remember { mutableStateOf(false) }

            println("Movie: ${movieViewState.movie}")

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