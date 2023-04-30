package com.example.mad_movie_app.models

import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * DetailScreenViewModel is a ViewModel class responsible for managing data and operations specific to the detail screen.
 *
 * @param movieRepository A MovieRepository instance to interact with the movie data.
 * @param movieId The ID of the movie to be displayed on the detail screen.
 * @param sharedFavoriteViewModel A SharedFavoriteViewModel instance to manage favorite movies.
 */

class DetailScreenViewModel(movieRepository: MovieRepository, movieId: String, private val sharedFavoriteViewModel: SharedFavoriteViewModel) : MovieViewModel(movieRepository, sharedFavoriteViewModel) {

    // declare private MutableStateFlow to hold the movie object for the detail screen
    private val _movie = MutableStateFlow<Movie?>(null)

    // expose immutable StateFlow to provide access to the movie object
    val movie: StateFlow<Movie?> = _movie

    // override favoriteMovies property to return the favoriteSet from the superclass
    override val favoriteMovies: StateFlow<Set<Movie>>
        get() = favoriteSet

    // initialize ViewModel by loading the movie with the specified movieId from the repository
    init {
        viewModelScope.launch {
            movieRepository.getMovieById(movieId).collect { movie ->
                _movie.value = movie
            }
        }
    }

    // override toggleFavorite() method to use sharedFavoriteViewModel
    override fun toggleFavorite(movieId: String) {
        sharedFavoriteViewModel.toggleFavorite(movieId)
    }

    // override isFavoriteMovie() method to use sharedFavoriteViewModel
    override fun isFavoriteMovie(movieId: String): Boolean {
        return sharedFavoriteViewModel.isFavoriteMovie(movieId)
    }

    // override deleteMovie() method to delete the movie and update the favorite set
    override suspend fun deleteMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieRepository.deleteMovie(movie)
        }
        if (favoriteSet.value.contains(movie)) {
            favoriteSet.value = (favoriteSet.value - movie).toSet()
        }
    }

}
