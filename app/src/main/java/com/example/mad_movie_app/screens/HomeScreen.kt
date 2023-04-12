package com.example.mad_movie_app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.mad_movie_app.components.MovieList
import com.example.mad_movie_app.components.loadMovies
import com.example.mad_movie_app.navigation.Screen
import com.example.mad_movie_app.navigation.TopBar

@Composable
fun HomeScreen(navController: NavController) {

    val movies = loadMovies()

    Column {
        TopBar(onFavoriteClick = { navController.navigate(Screen.Favorite.route) })
        MovieList(
            movies = movies,
            onMovieClick = { movieId ->
                navController.navigate(Screen.Detail.route.replace("{movieId}", movieId))
            }
        )
    }
}