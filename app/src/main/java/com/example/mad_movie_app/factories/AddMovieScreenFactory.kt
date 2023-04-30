package com.example.mad_movie_app.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.models.AddMovieScreenViewModel
import com.example.mad_movie_app.models.SharedFavoriteViewModel

class AddMovieScreenFactory(private val movieRepository: MovieRepository, private val sharedFavoriteViewModel: SharedFavoriteViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMovieScreenViewModel::class.java)) {
            return AddMovieScreenViewModel(movieRepository, sharedFavoriteViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

