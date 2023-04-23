package com.example.mad_movie_app.models

import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddMovieScreenViewModel(movieRepository: MovieRepository) : BaseMovieViewModel(movieRepository) {
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
        val isValid = title.isNotBlank() && year.isNotBlank() && genres.isNotEmpty() &&
                director.isNotBlank() && actors.isNotBlank() && plot.isNotBlank() &&
                (rating != null && rating in 0.0..10.0)

        _isFormValid.value = isValid
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.addMovie(movie)
        }
    }
}

