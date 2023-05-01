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

/**
 * A composable function that displays a movie card with a poster, details, and favorite toggle.
 *
 * @param movie The Movie object containing the movie details.
 * @param isFavorite A boolean indicating if the movie is a favorite.
 * @param onMovieClick A lambda function to be executed when the movie card is clicked, with the movie ID as its parameter.
 * @param onFavoriteClick A lambda function to be executed when the favorite button is clicked.
 * @param isExpanded A MutableState<Boolean> indicating if the movie card details are expanded or not.
 * @param onExpandClick A lambda function to be executed when the expand/collapse icon is clicked.
 */

@Composable
fun MovieCard(
    movie: Movie,
    isFavorite: Boolean,
    onMovieClick: (String) -> Unit,
    onFavoriteClick: () -> Unit,
    isExpanded: MutableState<Boolean>,
    onExpandClick: () -> Unit
) {

    // remember favorite state

    val isFavoriteState = remember { mutableStateOf(isFavorite) }

    // create card component

    Card(
        modifier = Modifier
            .clickable { onMovieClick(movie.id) }
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column {

            // display movie poster with favorite toggle icon

            MoviePoster(
                posterUrl = movie.images.firstOrNull() ?: "https://www.saugertieslighthouse.com/slc/wp-content/themes/u-design/assets/images/placeholders/post-placeholder.jpg",
                isFavorite = isFavoriteState.value,
                onMovieClick = { onMovieClick(movie.id) },
                onFavoriteClick = {
                    onFavoriteClick()
                    isFavoriteState.value = !isFavoriteState.value
                }
            )

            // display movie card details

            MovieCardDetails(
                movie = movie,
                isExpanded = isExpanded.value,
                onClick = onExpandClick
            )
        }
    }
}
