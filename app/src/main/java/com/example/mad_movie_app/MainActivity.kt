package com.example.mad_movie_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.mad_movie_app.data.MovieDao
import com.example.mad_movie_app.data.MovieDatabase
import com.example.mad_movie_app.data.MovieRepository
import com.example.mad_movie_app.factories.*
import com.example.mad_movie_app.models.*
import com.example.mad_movie_app.navigation.MyNavigation
import com.example.mad_movie_app.ui.theme.MAD_Movie_AppTheme

class MainActivity : ComponentActivity() {

    // declare view model properties
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var detailScreenViewModel: DetailScreenViewModel
    private lateinit var addMovieScreenViewModel: AddMovieScreenViewModel
    private lateinit var favoriteViewModel: SharedFavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instantiate the database
        val database = MovieDatabase.getInstance(this)

        // Get the MovieDao instance from the database
        val movieDao: MovieDao = database.movieDao()

        // Instantiate the repository with the MovieDao instance
        val repository = MovieRepository(movieDao)


        // Instantiate the SharedFavoriteViewModel
        val sharedFavoriteViewModel = ViewModelProvider(this, FavoriteFactory(repository))[SharedFavoriteViewModel::class.java]

        // Pass the SharedFavoriteViewModel to the other view models
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(repository, sharedFavoriteViewModel))[MovieViewModel::class.java]
        addMovieScreenViewModel = ViewModelProvider(this, AddMovieScreenFactory(repository, sharedFavoriteViewModel))[AddMovieScreenViewModel::class.java]
        favoriteViewModel = ViewModelProvider(this).get(SharedFavoriteViewModel::class.java)

        setContent {
            MAD_Movie_AppTheme {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntry?.destination?.route

                // Get the movieId from the route arguments
                val movieId = if (currentRoute == "detail") {
                    navController.currentBackStackEntry?.arguments?.getString("movieId")
                } else {
                    null
                }

                if (movieId != null) {
                    detailScreenViewModel = ViewModelProvider(
                        this,
                        DetailScreenFactory(repository, movieId, sharedFavoriteViewModel)
                    )[DetailScreenViewModel::class.java]
                }

                MyNavigation(movieViewModel, addMovieScreenViewModel, favoriteViewModel, repository)
            }
        }
    }
}
