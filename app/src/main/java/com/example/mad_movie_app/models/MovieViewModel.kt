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

    // expose an immutable StateFlow to provide access to the favorite movies list from SharedFavoriteViewModel

    open val favoriteMovies: StateFlow<Set<Movie>> = sharedFavoriteViewModel.favoriteMovies

    // initialize the ViewModel by loading movies and favorite movies from the repository

    init {
        viewModelScope.launch {
            movieRepository.getMovies().collect { movies ->
                Log.d("MovieViewModel", "Movies loaded: ${movies.size}")
                _movies.value = movies
            }
        }
    }


    // add a new function to add a movie to the _movies list
    fun addMovieToList(movie: Movie) {
        _movies.value = _movies.value + movie
    }


    /**
     * deleteMovie(): deletes the specified movie from the movie repository.
     *
     * @param movie The movie object to be deleted.
     */


    //  when suspend function is called within a coroutine
    //  it is suspended until the function completes
    //  but other coroutines can continue executing in the meantime

    open suspend fun deleteMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieRepository.deleteMovie(movie)
        }

        // remove the movie from the favorite list if it exists

        if (favoriteMovies.value.contains(movie)) {
            sharedFavoriteViewModel.toggleFavorite(movie.id)
        }
    }
}
