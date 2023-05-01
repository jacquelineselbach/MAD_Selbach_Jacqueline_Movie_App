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
import com.example.mad_movie_app.navigation.Navigation
import com.example.mad_movie_app.ui.theme.MAD_Movie_AppTheme

/**
 * MainActivity is the entry point of the MAD Movie App. It sets up the
 * database and the view models, and displays the UI using the Compose UI
 * toolkit.
 */

class MainActivity : ComponentActivity() {

    // declare late initialization view model properties
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var detailScreenViewModel: DetailScreenViewModel
    private lateinit var addMovieScreenViewModel: AddMovieScreenViewModel


    // override onCreate method of ComponentActivity class
    // called when activity is created

    override fun onCreate(savedInstanceState: Bundle?) {

        // invoke the parent class's implementation of the onCreate method
        // perform default initialization of the activity

        super.onCreate(savedInstanceState)

        // instantiate database

        val database = MovieDatabase.getInstance(this)

        // get MovieDao instance from database

        val movieDao: MovieDao = database.movieDao()

        // instantiate repository with MovieDao instance

        val repository = MovieRepository(movieDao)

        // instantiate SharedFavoriteViewModel

        val sharedFavoriteViewModel = ViewModelProvider(this, FavoriteFactory(repository))[SharedFavoriteViewModel::class.java]

        // instantiate other ViewModels and pass SharedFavoriteViewModel

        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(repository, sharedFavoriteViewModel))[MovieViewModel::class.java]
        addMovieScreenViewModel = ViewModelProvider(this, AddMovieScreenFactory(repository, sharedFavoriteViewModel))[AddMovieScreenViewModel::class.java]

        setContent {

            // set content of top-level compose function MAD_Movie_AppTheme

            MAD_Movie_AppTheme {

                // initialize navController

                val navController = rememberNavController()

                // navController.currentBackStackEntry returns the topmost entry on the back stack
                // destination property returns the current destination of the navigation represented by NavDestination object
                // route property of NavDestination object returns unique identifier for the destination

                val currentRoute = navController.currentBackStackEntry?.destination?.route

                // get movieId from the route arguments

                val movieId = if (currentRoute == "detail") {
                    navController.currentBackStackEntry?.arguments?.getString("movieId")
                } else {
                    null
                }

                // if not null instantiate the DetailScreenViewModel using the ViewModelProvider

                if (movieId != null) {
                    detailScreenViewModel = ViewModelProvider(
                        this,
                        DetailScreenFactory(repository, movieId, sharedFavoriteViewModel)
                    )[DetailScreenViewModel::class.java]
                }

                // create an instance of the MyNavigation Composable function

                Navigation(movieViewModel, addMovieScreenViewModel, sharedFavoriteViewModel, repository)
            }
        }
    }

    /*
    /** onStart(): called when the activity is starting */
    override fun onStart() {
        super.onStart() }

    /** onResume(): called when the activity is becoming visible to the user */
    override fun onResume() {
        super.onResume()
    }
    /** onPause(): called when the activity is going into the background, but has not been killed */
    override fun onPause() {
        super.onPause() }

    /** onStop(): called when the activity is no longer visible to the user */
    override fun onStop() {
        super.onStop() }

    /** onDestroy(): called when the activity is being destroyed */
    override fun onDestroy() {
        super.onDestroy() }
     */
}
