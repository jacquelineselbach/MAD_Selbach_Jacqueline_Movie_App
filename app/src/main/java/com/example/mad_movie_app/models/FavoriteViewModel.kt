package com.example.mad_movie_app.models

import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(movieRepository: MovieRepository) : BaseMovieViewModel(movieRepository) {
    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies

    init {
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().collect { movies ->
                _favoriteMovies.value = movies
            }
        }
    }
}