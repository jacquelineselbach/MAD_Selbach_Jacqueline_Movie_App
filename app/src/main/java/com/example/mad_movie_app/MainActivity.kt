package com.example.mad_movie_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.mad_movie_app.data.MovieDao
import com.example.mad_movie_app.data.MovieDatabase
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.factories.MovieViewModelFactory
import com.example.mad_movie_app.models.MovieViewModel
import com.example.mad_movie_app.navigation.MyNavigation
import com.example.mad_movie_app.ui.theme.MAD_Movie_AppTheme
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instantiate the database
        val database = MovieDatabase.getInstance(this)

        // Get the MovieDao instance from the database
        val movieDao: MovieDao = database.movieDao()

        // Instantiate the repository with the MovieDao instance
        val repository = MovieRepository(movieDao)

        // Initialize movies only if the database is empty
        runBlocking {
            repository.initializeMovies()
        }

        // Instantiate the ViewModel using the factory
        viewModel = ViewModelProvider(this, MovieViewModelFactory(repository)).get(MovieViewModel::class.java)

        setContent {
            MAD_Movie_AppTheme {
                MyNavigation(viewModel)
            }
        }
    }
}
