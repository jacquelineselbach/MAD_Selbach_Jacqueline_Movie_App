package com.example.mad_movie_app.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.models.DetailScreenViewModel

class DetailScreenFactory(
    private val movieRepository: MovieRepository,
    private val movieId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            return DetailScreenViewModel(movieRepository, movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
