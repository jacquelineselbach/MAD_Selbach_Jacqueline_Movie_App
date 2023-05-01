package com.example.mad_movie_app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * MovieDao is an interface that defines the database operations for the Movie entity.
 * It serves as the Data Access Object (DAO) for the Room database.
 */

@Dao
interface MovieDao {

    /**
     * Retrieves all movies from the database.
     *
     * @return A Flow of a list of all movies.
     */

    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<Movie>>

    /**
     * Retrieves a movie with the specified movie ID from the database.
     *
     * @param movieId The ID of the movie to be fetched.
     * @return A Flow of the movie with the given ID, or null if not found.
     */

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: String): Flow<Movie?>

    /**
     * Updates all fields in the database row corresponding to the passed-in entity object
     * using the row's primary key as the identifier.
     *
     * @param movie The movie to be updated.
     */

    @Update
    suspend fun updateMovie(movie: Movie)

    /**
     *  Takes an object of the entity type as a parameter
     *  and deletes the corresponding row from the database.
     *
     * @param movie The movie to be deleted.
     */

    @Delete
    suspend fun deleteMovie(movie: Movie)

    /**
     * Deletes a movie with the specified movie ID from the database.
     *
     * @param movieId The ID of the movie to be deleted.
     */

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: String)

    /**
     * Inserts a movie into the database.
     * If a movie with the same ID already exists, it will be replaced.
     *
     * @param movie The movie to be inserted.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    /**
     * Inserts a list of movies into the database.
     * If a movie with the same ID already exists, it will be replaced.
     *
     * @param movies The list of movies to be inserted.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    /**
     * Toggles the favorite status of a movie with the specified movie ID.
     *
     * @param movieId The ID of the movie to toggle its favorite status.
     */

    @Query("UPDATE movies SET favorite = NOT favorite WHERE id = :movieId")
    suspend fun toggleFavorite(movieId: String)

    /**
     * Checks if a movie with the specified movie ID is marked as favorite.
     *
     * @param movieId The ID of the movie to check its favorite status.
     * @return True if the movie is marked as favorite, false otherwise, or null if not found.
     */

    @Query("SELECT favorite FROM movies WHERE id = :movieId")
    fun isFavoriteMovie(movieId: String): Boolean?

    /**
     * Retrieves all favorite movies from the database.
     *
     * @return A Flow of a list of favorite movies.
     */

    @Query("SELECT * FROM movies WHERE favorite = 1")
    fun getFavoriteMovies(): Flow<List<Movie>>

    /**
     * Gets the total count of movies in the database.
     *
     * @return The number of movies in the database.
     */

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int
}
