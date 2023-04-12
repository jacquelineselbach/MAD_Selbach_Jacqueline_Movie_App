package com.example.mad_movie_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mad_movie_app.data.Movie

@Composable
fun MovieCard(
    movie: Movie,
    isFavorite: Boolean,
    onMovieClick: (String) -> Unit,
    onFavoriteClick: () -> Unit,
    isExpanded: MutableState<Boolean>,
    onExpandClick: () -> Unit
) {
    val isFavoriteState = remember { mutableStateOf(isFavorite) }

    Card(
        modifier = Modifier
            .clickable { onMovieClick(movie.id) }
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column {
            MoviePoster(
                posterUrl = movie.images.firstOrNull() ?: "https://www.saugertieslighthouse.com/slc/wp-content/themes/u-design/assets/images/placeholders/post-placeholder.jpg",
                isFavorite = isFavoriteState.value,
                onMovieClick = { onMovieClick(movie.id) },
                onFavoriteClick = {
                    onFavoriteClick()
                    isFavoriteState.value = !isFavoriteState.value
                }
            )
            MovieCardDetails(
                movie = movie,
                isExpanded = isExpanded.value,
                onClick = onExpandClick
            )
        }
    }
}