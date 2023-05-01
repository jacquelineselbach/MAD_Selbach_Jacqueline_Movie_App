package com.example.mad_movie_app.models

import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * DetailScreenViewModel is a ViewModel class responsible for managing data and operations specific to the detail screen.
 *
 * @param movieRepository A MovieRepository instance to interact with the movie data.
 * @param movieId The ID of the movie to be displayed on the detail screen.
 * @param sharedFavoriteViewModel A SharedFavoriteViewModel instance to manage favorite movies.
 */

class DetailScreenViewModel(movieRepository: MovieRepository, movieId: String, sharedFavoriteViewModel: SharedFavoriteViewModel) : MovieViewModel(movieRepository, sharedFavoriteViewModel) {

    // declare private MutableStateFlow to hold a single movie object for the detail screen

    private val _movie = MutableStateFlow<Movie?>(null)

    // expose immutable StateFlow to provide access to the movie object

    val movie: StateFlow<Movie?> = _movie

    // initialize ViewModel by finding movie with the specified movieId from the movies property

    init {
        viewModelScope.launch {
            _movie.value = movies.value.find { it.id == movieId }
        }
    }

    // no need to override toggleFavorite(), isFavoriteMovie(), and deleteMovie() methods since they already use sharedFavoriteViewModel in MovieViewModel
}
