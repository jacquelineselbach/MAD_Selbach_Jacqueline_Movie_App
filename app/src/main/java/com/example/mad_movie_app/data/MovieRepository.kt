package com.example.mad_movie_app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

/**
 * MovieRepository is a class responsible for managing movie repository data and operations.
 *
 * @param movieDao A MovieDao instance to interact with the movie database.
 */

class MovieRepository(private val movieDao: MovieDao) {

    /**
     * Add a movie to the database.
     *
     * @param movie The movie to be added.
     */

    suspend fun addMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    /**
     * Delete a movie from the database.
     *
     * @param movieId The movie to be deleted.
     */

    suspend fun deleteMovie(movieId: Movie) {
        movieDao.deleteMovie(movieId)
    }

    /**
     * Get all movies from the database.
     *
     * @return A Flow of a list of movies.
     */

    fun getMovies(): Flow<List<Movie>> {
        return movieDao.getMovies()
    }

    /**
     * Get a movie by its ID from the database.
     *
     * @param movieId The ID of the movie to be fetched.
     * @return A Flow of the movie with the given ID, or null if not found.
     */

    fun getMovieById(movieId: String): Flow<Movie?> {
        return movieDao.getMovieById(movieId)
    }

    /**
     * Get all favorite movies from the database.
     *
     * @return A Flow of a list of favorite movies.
     */

    fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies()
    }

    /**
     * Toggle the favorite status of a movie with the given movieId.
     *
     * @param movieId The ID of the movie to toggle its favorite status.
     */

    suspend fun toggleFavorite(movieId: String) {

        // toggles favorite status of a movie and updates it in the database.
        // Since it performs database operations
        // it is marked as a suspend function and is called from a coroutine

        withContext(Dispatchers.IO) {
            val movie = movieDao.getMovieById(movieId).firstOrNull()
            if (movie != null) {
                val updatedMovie = movie.copy(favorite = !movie.favorite)
                movieDao.updateMovie(updatedMovie)
            }
        }
    }

}
