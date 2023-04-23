package com.example.mad_movie_app.models

import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(movieViewModel: MovieViewModel) : BaseMovieViewModel(movieViewModel.movieRepository) {
    private val _favoriteMovies = MutableStateFlow<Set<Movie>>(emptySet())
    val favoriteMovies: StateFlow<Set<Movie>> = _favoriteMovies

    init {
        viewModelScope.launch {
            movieViewModel.movies.collect { movies ->
                _favoriteMovies.value = movies.filter { it.favorite }.toSet()
            }
        }
    }
}
