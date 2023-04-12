package com.example.mad_movie_app.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun MovieList(
    movies: List<Movie>,
    onMovieClick: (movieId: String) -> Unit) {

    LazyColumn {
        items(movies) { movie ->
            val isFavorite = remember { mutableStateOf(true) }
            MovieCard(
                movie = movie,
                onMovieClick = { onMovieClick(movie.id) },
                onFavoriteClick = { isFavorite.value = !isFavorite.value },
                isFavorite = isFavorite.value
            )
        }
    }
}