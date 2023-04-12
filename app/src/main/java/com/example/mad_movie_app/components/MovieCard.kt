package com.example.mad_movie_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MovieCard(
    movie: Movie,
    isFavorite: Boolean,
    onMovieClick: (String) -> Unit,
    onFavoriteClick: () -> Unit
) {
    val expandedState = remember { mutableStateOf(false) }
    val favoriteState = remember { mutableStateOf(isFavorite) }

    Card(
        modifier = Modifier
            .clickable { onMovieClick(movie.id) }
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column {
            MoviePoster(
                posterUrl = movie.images.first(),
                onFavoriteClick = {
                    favoriteState.value = !favoriteState.value
                    onFavoriteClick()
                }
            ) { onMovieClick(movie.id) }
            MovieCardDetails(
                movie = movie,
                isExpanded = expandedState.value,
                onClick = { expandedState.value = !expandedState.value }
            )
        }
    }
}