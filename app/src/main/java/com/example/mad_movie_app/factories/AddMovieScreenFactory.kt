package com.example.mad_movie_app.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.models.AddMovieScreenViewModel
import com.example.mad_movie_app.models.SharedFavoriteViewModel

/**
 * AddMovieScreenFactory is a custom ViewModel factory class responsible for providing instances of AddMovieScreenViewModel.
 *
 * @param movieRepository A MovieRepository instance to interact with movie data.
 * @param sharedFavoriteViewModel A SharedFavoriteViewModel instance to manage favorite movies.
 */

class AddMovieScreenFactory(
    private val movieRepository: MovieRepository,
    private val sharedFavoriteViewModel: SharedFavoriteViewModel
) : ViewModelProvider.Factory {

    /**
     * Creates an instance of the ViewModel class requested.
     *
     * @param modelClass A Class whose instance is requested.
     * @return A newly created ViewModel of the given class.
     * @throws IllegalArgumentException If the requested ViewModel class is unknown.
     */

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMovieScreenViewModel::class.java)) {
            return AddMovieScreenViewModel(movieRepository, sharedFavoriteViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
