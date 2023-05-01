package com.example.mad_movie_app.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.models.MovieViewModel
import com.example.mad_movie_app.models.SharedFavoriteViewModel

/**
 * MovieViewModelFactory is a custom ViewModel factory class responsible for providing instances of MovieViewModel.
 *
 * @param movieRepository A MovieRepository instance to interact with movie data.
 * @param sharedFavoriteViewModel A SharedFavoriteViewModel instance to manage favorite movies.
 */

class MovieViewModelFactory(

    private val movieRepository: MovieRepository,
    private val sharedFavoriteViewModel: SharedFavoriteViewModel

) : ViewModelProvider.Factory {

    /**
     * Creates instance of the ViewModel class requested.
     *
     * @param modelClass A Class whose instance is requested.
     * @return A newly created ViewModel of the given class.
     * @throws IllegalArgumentException If the requested ViewModel class is unknown.
     */

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // check if requested ViewModel class is MovieViewModel or a subclass of it

        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {

            // suppress the unchecked cast warning, as the check above ensures the cast is valid

            @Suppress("UNCHECKED_CAST")

            // return new instance of MovieViewModel with the provided movieRepository and sharedFavoriteViewModel

            return MovieViewModel(movieRepository, sharedFavoriteViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
