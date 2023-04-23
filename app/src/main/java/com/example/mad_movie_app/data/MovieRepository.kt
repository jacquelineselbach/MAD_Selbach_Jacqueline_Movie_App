package com.example.mad_movie_app.data

import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.data.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    suspend fun addMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun deleteMovie(movieId: Movie) {
        movieDao.deleteMovie(movieId)
    }


    suspend fun toggleFavorite(movieId: String) {
        movieDao.toggleFavorite(movieId)
    }

    fun isFavoriteMovie(movieId: String): Boolean {
        return movieDao.isFavoriteMovie(movieId)
    }

    suspend fun initializeMovies() {
        withContext(Dispatchers.IO) {
            if (movieDao.getMovieCount() == 0) {
                movieDao.insertMovies(getDefaultMovies())
            }
        }
    }
}
