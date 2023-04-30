package com.example.mad_movie_app.models

import androidx.lifecycle.viewModelScope
import com.example.mad_movie_app.data.Genre
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.data.loadMovies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * AddMovieScreenViewModel is a ViewModel class responsible for managing data and operations specific to the add movie screen.
 *
 * @param movieRepository A MovieRepository instance to interact with the movie data.
 * @param sharedFavoriteViewModel A SharedFavoriteViewModel instance to manage favorite movies.
 */

class AddMovieScreenViewModel(movieRepository: MovieRepository, private val sharedFavoriteViewModel: SharedFavoriteViewModel) : MovieViewModel(movieRepository, sharedFavoriteViewModel) {

    // Declare a private MutableStateFlow to hold the list of movies
    private val _movies = MutableStateFlow(loadMovies())

    // Expose an immutable StateFlow to provide access to the list of movies
    override val movies: StateFlow<List<Movie>> = _movies

    // Declare a private MutableStateFlow to hold the form validation state
    private val _isFormValid = MutableStateFlow(false)

    // Expose an immutable StateFlow to provide access to the form validation state
    val isFormValid: StateFlow<Boolean> = _isFormValid

    /**
     * updateForm(): Updates the form validation state based on the provided input fields.
     *
     * @param title The title of the movie.
     * @param year The release year of the movie.
     * @param genres A list of genre titles for the movie.
     * @param director The director of the movie.
     * @param actors The actors in the movie.
     * @param plot The plot of the movie.
     * @param rating The rating of the movie.
     */

    fun updateForm(
        title: String,
        year: String,
        genres: List<String>,
        director: String,
        actors: String,
        plot: String,
        rating: Float?
    ) {
        // Convert the list of genre titles to a list of Genre objects
        val selectedGenres = genres.mapNotNull { genreTitle -> Genre.values().firstOrNull { it.toString() == genreTitle } }

        // Update the form validation state based on the input field values
        _isFormValid.value =
            title.isNotEmpty() &&
                    year.isNotEmpty() &&
                    plot.isNotEmpty() &&
                    selectedGenres.isNotEmpty() &&
                    director.isNotEmpty() &&
                    actors.isNotEmpty() && rating != null
    }

    /**
     * addMovie(): Adds the specified movie to the movie repository and updates the list of movies.
     *
     * @param movie The movie object to be added.
     */

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.addMovie(movie) // Use the repository to add a movie
        }
        _movies.value = _movies.value.plus(movie)
    }
}
