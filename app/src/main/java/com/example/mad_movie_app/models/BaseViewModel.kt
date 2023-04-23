package com.example.mad_movie_app.models

import androidx.lifecycle.ViewModel
import com.example.mad_movie_app.data.MovieRepository

open class BaseMovieViewModel(val movieRepository: MovieRepository) : ViewModel()
