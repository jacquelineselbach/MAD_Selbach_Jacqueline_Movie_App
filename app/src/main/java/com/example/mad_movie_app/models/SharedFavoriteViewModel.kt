package com.example.mad_movie_app.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedFavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow<Set<Movie>>(emptySet())

    val favoriteMovies: StateFlow<Set<Movie>>
        get() = _favoriteMovies

    init {
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().collect { movies ->
                Log.d("SharedFavoriteViewModel", "Favorite movies loaded: ${movies.size}")
                _favoriteMovies.value = movies.toSet()
            }
        }
    }

    fun toggleFavorite(movieId: String) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
            val movie = favoriteMovies.value.find { it.id == movieId }
            movie?.let {
                if (isFavoriteMovie(movieId)) {
                    _favoriteMovies.value = (_favoriteMovies.value - movie).toSet()
                } else {
                    _favoriteMovies.value = (_favoriteMovies.value + movie).toSet()
                }
            }
        }
    }

    fun isFavoriteMovie(movieId: String): Boolean {
        return _favoriteMovies.value.any { it.id == movieId }
    }
}
