package com.example.mad_movie_app.models

import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch

class FavoriteViewModel(movieViewModel: MovieViewModel) : BaseMovieViewModel(movieViewModel.movieRepository) {
    private val _favoriteMovies = MutableStateFlow<Set<Movie>>(emptySet())
    val favoriteMovies: StateFlow<Set<Movie>> = _favoriteMovies

    init {
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().collect { movies ->
                _favoriteMovies.value = movies.toSet()
            }
        }
    }

    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            val favoriteMovie = _favoriteMovies.value.find { it.id == id }
            if (favoriteMovie != null) {
                val updatedFavorite = favoriteMovie.copy(favorite = !favoriteMovie.favorite)
                _favoriteMovies.value = _favoriteMovies.value - favoriteMovie + updatedFavorite
                movieRepository.updateFavorite(id, updatedFavorite.favorite)
            }
        }
    }
}


