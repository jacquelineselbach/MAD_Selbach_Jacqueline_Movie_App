package com.example.mad_movie_app.models

import androidx.lifecycle.ViewModel
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.data.loadMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log
import androidx.lifecycle.viewModelScope

/**
 * MovieViewModel is a ViewModel class responsible for managing movie data and operations.
 *
 * @param movieRepository A MovieRepository instance to interact with the movie data.
 * @param sharedFavoriteViewModel A SharedFavoriteViewModel instance to manage favorite movies.
 */

open class MovieViewModel(val movieRepository: MovieRepository, private val sharedFavoriteViewModel: SharedFavoriteViewModel) : ViewModel() {

    // declare a private MutableStateFlow to hold the movies list
    private val _movies = MutableStateFlow(loadMovies())

    // expose an immutable StateFlow to provide access to the movies list
    open val movies: StateFlow<List<Movie>> = _movies

    // declare a private MutableStateFlow to hold the favorite movies set
    val favoriteSet = MutableStateFlow<Set<Movie>>(emptySet())

    // expose an immutable StateFlow to provide access to the favorite movies set
    open val favoriteMovies: StateFlow<Set<Movie>> = favoriteSet

    // initialize the ViewModel by loading movies and favorite movies from the repository
    init {
        viewModelScope.launch {
            movieRepository.getMovies().collect { movies ->
                Log.d("MovieViewModel", "Movies loaded: ${movies.size}")
                _movies.value = movies
            }
            movieRepository.getFavoriteMovies().collect { movies ->
                Log.d("FavoriteViewModel", "Favorite movies loaded: ${movies.size}")
                favoriteSet.value = movies.toSet()
            }
        }
    }

    /**
     * toggleFavorite(): toggles the favorite status of a movie with the given movieId.
     *
     * @param movieId The ID of the movie to toggle its favorite status.
     */

    open fun toggleFavorite(movieId: String) {
        sharedFavoriteViewModel.toggleFavorite(movieId)
    }

    /**
     * isFavoriteMovie(): checks if a movie with the given movieId is in the favorite movies set.
     *
     * @param movieId The ID of the movie to check its favorite status.
     * @return True if the movie is in the favorite movies set, false otherwise.
     */

    open fun isFavoriteMovie(movieId: String): Boolean {
        return sharedFavoriteViewModel.isFavoriteMovie(movieId)
    }

    /**
     * deleteMovie(): deletes the specified movie from the movie repository.
     *
     * @param movie The movie object to be deleted.
     */

    open suspend fun deleteMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieRepository.deleteMovie(movie)
        }
        // remove the movie from the favorite list if it exists
        if (favoriteSet.value.contains(movie)) {
            favoriteSet.value = (favoriteSet.value - movie).toSet()
        }
    }
}
