package com.example.mad_movie_app.data

data class MovieViewState(
    val movie: Movie,
    var isFavorite: Boolean = false
)
