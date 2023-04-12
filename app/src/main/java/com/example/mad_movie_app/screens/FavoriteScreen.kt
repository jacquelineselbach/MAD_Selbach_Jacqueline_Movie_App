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
import com.example.mad_movie_app.models.MovieViewModel
import com.example.mad_movie_app.navigation.SimpleAppBar

@Composable
fun FavoriteScreen(navController: NavHostController, viewModel: MovieViewModel, onMovieClick: (String) -> Unit) {
    val favoriteMovies = viewModel.favoriteMovies.collectAsState().value
    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SimpleAppBar(
            title = "Favorite Movies",
            onBackClick = { navController.popBackStack() }
        )

        if (favoriteMovies.isEmpty()) {
            Text(
                text = "You have not added any favorite movies yet!",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(favoriteMovies, key = { movie -> movie.id }) { movie -> // Use the key parameter here
                    val isExpanded = remember { mutableStateOf(false) }

                    MovieCard(
                        movie = movie,
                        isFavorite = viewModel.isFavoriteMovie(movie.id),
                        onMovieClick = onMovieClick,
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