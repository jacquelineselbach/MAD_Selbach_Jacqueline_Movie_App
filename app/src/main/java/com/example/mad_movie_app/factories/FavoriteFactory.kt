package com.example.mad_movie_app.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.models.FavoriteViewModel
import com.example.mad_movie_app.models.MovieViewModel

class FavoriteFactory(
    private val movieViewModel: MovieViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(movieViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
