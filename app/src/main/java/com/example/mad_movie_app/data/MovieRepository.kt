package com.example.mad_movie_app.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class MovieRepository(private val movieDao: MovieDao) {

    fun getMovies(): Flow<List<Movie>> {
        return movieDao.getMovies()
    }

    fun getMovieById(movieId: String): Flow<Movie?> {
        return movieDao.getMovieById(movieId)
    }

    fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies()
    }

    suspend fun updateFavorite(movieId: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            val movie = movieDao.getMovieById(movieId).firstOrNull()
            if (movie != null) {
                val updatedMovie = movie.copy(favorite = isFavorite)
                movieDao.updateMovie(updatedMovie)
            }
        }
    }

    suspend fun addMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun deleteMovie(movieId: Movie) {
        movieDao.deleteMovie(movieId)
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
                Log.d("MovieRepository", "Initializing default movies")
                movieDao.insertMovies(getDefaultMovies())
                Log.d("MovieRepository", "Default movies inserted")
            } else {
                Log.d("MovieRepository", "Movies already in database")
            }
        }
    }


}
