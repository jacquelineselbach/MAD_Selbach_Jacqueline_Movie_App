package com.example.mad_movie_app.models

import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel(movieRepository: MovieRepository, movieId: String) : BaseMovieViewModel(movieRepository) {
    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    init {
        viewModelScope.launch {
            movieRepository.getMovieById(movieId).collect { movie ->
                _movie.value = movie
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _movie.value?.id?.let { movieRepository.toggleFavorite(it) }
        }
    }
}