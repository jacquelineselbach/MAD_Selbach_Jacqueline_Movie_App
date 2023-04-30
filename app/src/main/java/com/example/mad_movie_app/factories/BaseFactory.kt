package com.example.mad_movie_app.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.models.DetailScreenViewModel
import com.example.mad_movie_app.models.MovieViewModel
import com.example.mad_movie_app.models.SharedFavoriteViewModel

class BaseFactory(
    private val movieRepository: MovieRepository,
    private val movieId: String? = null,
    private val sharedFavoriteViewModel: SharedFavoriteViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieRepository, sharedFavoriteViewModel) as T
            }
            modelClass.isAssignableFrom(DetailScreenViewModel::class.java) -> {
                if (movieId == null) {
                    throw IllegalStateException("movieId must not be null for DetailScreenViewModel")
                }
                DetailScreenViewModel(movieRepository, movieId, sharedFavoriteViewModel) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
