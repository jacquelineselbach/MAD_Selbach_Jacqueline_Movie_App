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
import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.getMovies

/**
 * MovieViewModel is a ViewModel class responsible for managing movie data and operations.
 *
 * @param movieRepository A MovieRepository instance to interact with the movie data.
 * @param sharedFavoriteViewModel A SharedFavoriteViewModel instance to manage favorite movies.
 */

open class MovieViewModel(val movieRepository: MovieRepository,
                          private val sharedFavoriteViewModel: SharedFavoriteViewModel) : ViewModel() {

    // declare a private MutableStateFlow to hold the movies list

    private val _movies = MutableStateFlow(loadMovies())

    // expose an immutable StateFlow to provide access to the movies list

    open val movies: StateFlow<List<Movie>> = _movies

    // expose an immutable StateFlow to provide access to the favorite movies list from SharedFavoriteViewModel

    open val favoriteMovies: StateFlow<Set<Movie>> = sharedFavoriteViewModel.favoriteMovies

    // initialize the ViewModel by loading movies and favorite movies from the repository

    init {
        viewModelScope.launch {

            // in case there are 0 movies in the database

            if (movieRepository.getMovieCount() == 0) {
                movieRepository.insertMovies(getMovies())
            }

            // fetch list of movies from the movie repository

            movieRepository.getMovies().collect { movies ->

                // update LiveData _movies with new list of seeded default movies

                _movies.value = movies
            }
        }
    }

    /**
     * Adds a given movie to the list of movies and updates the LiveData.
     *
     * @param movie The [Movie] object to be added to the list.
     */

    fun addMovieToList(movie: Movie) {
        _movies.value = _movies.value + movie
    }

    /**
     * deleteMovie(): deletes the specified movie from the movie repository.
     *
     * @param movie The movie object to be deleted.
     */

    open suspend fun deleteMovie(movie: Movie) {

        //  when suspend function is called within a coroutine
        //  it is suspended until the function completes
        //  but other coroutines can continue executing in the meantime

        withContext(Dispatchers.IO) {
            movieRepository.deleteMovie(movie)
        }

        // remove the movie from the favorite list if it exists

        if (favoriteMovies.value.contains(movie)) {
            sharedFavoriteViewModel.toggleFavorite(movie.id)
        }
    }
}
