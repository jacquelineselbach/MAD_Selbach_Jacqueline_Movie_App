package com.example.mad_movie_app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun addMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun updateMovie(movie: Movie) {
        movieDao.updateMovie(movie)
    }

    suspend fun deleteMovie(movieId: Movie) {
        movieDao.deleteMovie(movieId)
    }

    fun getMovies(): Flow<List<Movie>> {
        return movieDao.getMovies()
    }

    fun getMovieById(movieId: String): Flow<Movie?> {
        return movieDao.getMovieById(movieId)
    }

    fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies()
    }

    suspend fun toggleFavorite(movieId: String) {
        withContext(Dispatchers.IO) {
            val movie = movieDao.getMovieById(movieId).firstOrNull()
            if (movie != null) {
                val updatedMovie = movie.copy(favorite = !movie.favorite)
                movieDao.updateMovie(updatedMovie)
            }
        }
    }

    suspend fun initializeMovies() {
        withContext(Dispatchers.IO) {
            if (movieDao.getMovieCount() == 0) {
                movieDao.insertMovies(getDefaultMovies())
            } else {
            }
        }
    }
}
