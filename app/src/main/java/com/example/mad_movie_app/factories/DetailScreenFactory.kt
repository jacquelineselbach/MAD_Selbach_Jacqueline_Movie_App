package com.example.mad_movie_app.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.models.DetailScreenViewModel
import com.example.mad_movie_app.models.SharedFavoriteViewModel

/**
 * DetailScreenFactory is a custom ViewModel factory class responsible for providing instances of DetailScreenViewModel.
 *
 * @param movieRepository A MovieRepository instance to interact with movie data.
 * @param movieId The ID of the movie to be displayed on the detail screen.
 * @param sharedFavoriteViewModel A SharedFavoriteViewModel instance to manage favorite movies.
 */

class DetailScreenFactory(
    private val movieRepository: MovieRepository,
    private val movieId: String,
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
        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            return DetailScreenViewModel(movieRepository, movieId, sharedFavoriteViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
