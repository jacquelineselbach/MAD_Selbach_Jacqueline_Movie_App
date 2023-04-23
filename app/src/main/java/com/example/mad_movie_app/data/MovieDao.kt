package com.example.mad_movie_app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: String): Flow<Movie?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("UPDATE movies SET favorite = NOT favorite WHERE id = :movieId")
    suspend fun toggleFavorite(movieId: String)

    @Query("SELECT favorite FROM movies WHERE id = :movieId")
    fun isFavoriteMovie(movieId: String): Boolean

    @Query("SELECT * FROM movies WHERE favorite = 1")
    fun getFavoriteMovies(): Flow<List<Movie>>

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int
}
