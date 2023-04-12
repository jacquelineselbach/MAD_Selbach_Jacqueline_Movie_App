package com.example.mad_movie_app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.mad_movie_app.components.MovieCard
import com.example.mad_movie_app.components.getMovies
import com.example.mad_movie_app.navigation.SimpleAppBar

@Composable
fun FavoriteScreen(navController: NavHostController) {

    val favoriteMovies = getMovies().subList(0, 3)
    val listState = rememberLazyListState()

    Column {

        SimpleAppBar(title = "Favorites", onBackClick = { navController.popBackStack() })

        LazyColumn(state = listState) {
            items(favoriteMovies) { movie ->
                val isFavorite = remember { mutableStateOf(true) }
                MovieCard(
                    movie = movie,
                    onMovieClick = { movieId -> navController.navigate("detailscreen/$movieId") },
                    isFavorite = isFavorite.value,
                    onFavoriteClick = { isFavorite.value = !isFavorite.value }
                )
            }
        }
    }
}