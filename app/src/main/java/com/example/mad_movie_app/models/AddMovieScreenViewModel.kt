package com.example.mad_movie_app.models

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Genre
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.data.loadMovies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddMovieScreenViewModel(movieRepository: MovieRepository, private val sharedFavoriteViewModel: SharedFavoriteViewModel) : MovieViewModel(movieRepository, sharedFavoriteViewModel)
 {

    private val _movies = MutableStateFlow(loadMovies())
    override val movies: StateFlow<List<Movie>> = _movies

    private val _isFormValid = MutableStateFlow(false)
    val isFormValid: StateFlow<Boolean> = _isFormValid

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

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.addMovie(movie) // Use the repository to add a movie
        }
        _movies.value = _movies.value.plus(movie)
        Log.d(MovieViewModel.TAG, "Movie added: $movie")
    }
}

