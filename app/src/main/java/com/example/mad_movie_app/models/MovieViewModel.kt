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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieViewModel(repository: MovieRepository) : BaseMovieViewModel(repository) {

    val favoriteMovies: StateFlow<Set<Movie>>
        get() = _favoriteMovies

    private val _favoriteMovies = MutableStateFlow<Set<Movie>>(emptySet())

    private val _movies = MutableStateFlow(loadMovies())
    val movies: StateFlow<List<Movie>> = _movies

    private val _isFormValid = MutableStateFlow(false)
    val isFormValid: StateFlow<Boolean> = _isFormValid

    init {
        viewModelScope.launch {
            movieRepository.getMovies().collect { movies ->
                Log.d("MovieViewModel", "Movies loaded: ${movies.size}")
                _movies.value = movies
            }
            movieRepository.getFavoriteMovies().collect { movies ->
                Log.d("MovieViewModel", "Favorite movies loaded: ${movies.size}")
                _favoriteMovies.value = movies.toSet()
            }
        }
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
                _favoriteMovies.value = (_favoriteMovies.value - movie).toSet()
            } else {
                _favoriteMovies.value = (_favoriteMovies.value + movie).toSet()
            }
        }
    }

    fun isFavoriteMovie(movieId: String): Boolean {
        return _favoriteMovies.value.any { it.id == movieId }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.updateMovie(movie)
        }
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.addMovie(movie) // Use the repository to add a movie
        }
        _movies.value = _movies.value.plus(movie)
        Log.d(TAG, "Movie added: $movie")
    }

    suspend fun deleteMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieRepository.deleteMovie(movie)
        }
        // Remove the movie from the favorite list if it exists
        if (_favoriteMovies.value.contains(movie)) {
            _favoriteMovies.value = (_favoriteMovies.value - movie).toSet()
        }
    }
}