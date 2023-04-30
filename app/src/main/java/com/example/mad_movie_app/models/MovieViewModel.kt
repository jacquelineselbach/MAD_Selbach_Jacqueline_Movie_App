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

open class MovieViewModel(val movieRepository: MovieRepository, private val sharedFavoriteViewModel: SharedFavoriteViewModel) : ViewModel(){
    private val _movies = MutableStateFlow(loadMovies())
    open val movies: StateFlow<List<Movie>> = _movies

    val _favoriteMovies = MutableStateFlow<Set<Movie>>(emptySet())
    open val favoriteMovies: StateFlow<Set<Movie>> = _favoriteMovies

    init {
        viewModelScope.launch {
            movieRepository.getMovies().collect { movies ->
                Log.d("MovieViewModel", "Movies loaded: ${movies.size}")
                _movies.value = movies
            }
            movieRepository.getFavoriteMovies().collect { movies ->
                Log.d("FavoriteViewModel", "Favorite movies loaded: ${movies.size}")
                _favoriteMovies.value = movies.toSet()
            }
        }
    }

    companion object {
        const val TAG = "MovieViewModel"
    }

    open fun toggleFavorite(movieId: String) {
        sharedFavoriteViewModel.toggleFavorite(movieId)
    }

    open fun isFavoriteMovie(movieId: String): Boolean {
        return sharedFavoriteViewModel.isFavoriteMovie(movieId)
    }

    open suspend fun deleteMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieRepository.deleteMovie(movie)
        }
        // Remove the movie from the favorite list if it exists
        if (_favoriteMovies.value.contains(movie)) {
            _favoriteMovies.value = (_favoriteMovies.value - movie).toSet()
        }
    }
}
