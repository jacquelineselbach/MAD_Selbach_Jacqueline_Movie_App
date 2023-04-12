package com.example.mad_movie_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mad_movie_app.models.MovieViewModel
import com.example.mad_movie_app.navigation.MyNavigation
import com.example.mad_movie_app.ui.theme.MAD_Movie_AppTheme

class MainActivity : ComponentActivity() {
    private val viewModel = MovieViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MAD_Movie_AppTheme {
                MyNavigation(viewModel)
            }
        }
    }
}
