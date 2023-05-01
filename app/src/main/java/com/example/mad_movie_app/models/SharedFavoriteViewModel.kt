package com.example.mad_movie_app.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * SharedFavoriteViewModel is a ViewModel class responsible for managing favorite movies data and operations.
 *
 * @param movieRepository A MovieRepository instance to interact with the movie data.
 */

class SharedFavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    // declare private MutableStateFlow to hold the favorite movies set

    private val _favoriteMovies = MutableStateFlow<Set<Movie>>(emptySet())

    // expose immutable StateFlow to provide access to the favorite movies set

    val favoriteMovies: StateFlow<Set<Movie>>
        get() = _favoriteMovies

    // initialize ViewModel by loading favorite movies from the repository
    init {
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().collect { movies ->
                Log.d("SharedFavoriteViewModel", "Favorite movies loaded: ${movies.size}")
                _favoriteMovies.value = movies.toSet()
            }
        }
    }

    /**
     * toggleFavorite(): Toggles the favorite status of a movie with the given movieId.
     *
     * @param movieId The ID of the movie to toggle its favorite status.
     */

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

    /**
     * isFavoriteMovie(): checks if a movie with the given movieId is in the favorite movies set.
     *
     * @param movieId The ID of the movie to check its favorite status.
     * @return True if the movie is in the favorite movies set, false otherwise.
     */

    fun isFavoriteMovie(movieId: String): Boolean {
        return _favoriteMovies.value.any { it.id == movieId }
    }
}
