package com.example.mad_movie_app.models

import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.loadMovies
import com.example.mad_movie_app.data.Genre
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.example.mad_movie_app.data.MovieRepository

class MovieViewModel(repository: MovieRepository) : BaseMovieViewModel(repository) {

    private val _favoriteMovies = MutableStateFlow<List<Movie>>(listOf())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies

    private val _movies = MutableStateFlow(loadMovies())
    val movies: StateFlow<List<Movie>> = _movies

    private val _isFormValid = MutableStateFlow(false)
    val isFormValid: StateFlow<Boolean> = _isFormValid

    init {
        viewModelScope.launch {
            movieRepository.getMovies().collect { moviesList ->
                _movies.value = moviesList
            }
            movieRepository.initializeMovies()
        }
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.addMovie(movie) // Use the repository to add a movie
        }
        _movies.value = _movies.value.plus(movie)
        Log.d(TAG, "Movie added: $movie")
    }

    companion object {
        const val TAG = "MovieViewModel"
    }

    fun updateForm(
        title: String,
        year: String,
        genres: List<String>,
        director: String,
        actors: String,
        plot: String,
        rating: Float?
    ) {
        val selectedGenres = genres.mapNotNull { genreTitle -> Genre.values().firstOrNull { it.toString() == genreTitle } }

        _isFormValid.value =
            title.isNotEmpty() &&
                    year.isNotEmpty() &&
                    selectedGenres.isNotEmpty()
                    && director.isNotEmpty() &&
                    actors.isNotEmpty() && rating != null
    }

    fun toggleFavorite(movieId: String) {
        val movie = movies.value.find { it.id == movieId }
        movie?.let {
            if (isFavoriteMovie(movieId)) {
                _favoriteMovies.value = _favoriteMovies.value.filter { it.id != movieId }
            } else {
                _favoriteMovies.value = _favoriteMovies.value + movie
            }
        }
    }

    fun isFavoriteMovie(movieId: String): Boolean {
        return _favoriteMovies.value.any { it.id == movieId }
    }
}