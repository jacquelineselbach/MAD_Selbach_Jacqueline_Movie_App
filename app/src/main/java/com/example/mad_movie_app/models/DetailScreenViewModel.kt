package com.example.mad_movie_app.models

import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.factories.DetailScreenFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailScreenViewModel(movieRepository: MovieRepository, movieId: String, private val sharedFavoriteViewModel: SharedFavoriteViewModel) : MovieViewModel(movieRepository, sharedFavoriteViewModel) {

    companion object {
        fun createFactory(movieRepository: MovieRepository, movieId: String, sharedFavoriteViewModel: SharedFavoriteViewModel): DetailScreenFactory {
            return DetailScreenFactory(movieRepository, movieId, sharedFavoriteViewModel)
        }
    }

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    override val favoriteMovies: StateFlow<Set<Movie>>
        get() = _favoriteMovies

    init {
        viewModelScope.launch {
            movieRepository.getMovieById(movieId).collect { movie ->
                _movie.value = movie
            }
        }
    }

    override fun isFavoriteMovie(movieId: String): Boolean {
        return _favoriteMovies.value.any { it.id == movieId }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _movie.value?.id?.let { movieRepository.toggleFavorite(it) }
        }
    }

    override suspend fun deleteMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieRepository.deleteMovie(movie)
        }
        // Remove the movie from the favorite list if it exists
        if (_favoriteMovies.value.contains(movie)) {
            _favoriteMovies.value = (_favoriteMovies.value - movie).toSet()
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.updateMovie(movie)
        }
    }
}
