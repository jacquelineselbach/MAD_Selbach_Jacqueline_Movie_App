package com.example.mad_movie_app.screens

import android.annotation.SuppressLint
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.R
import com.example.mad_movie_app.data.Genre
import com.example.mad_movie_app.data.ListItemSelectable
import com.example.mad_movie_app.models.MovieViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.example.mad_movie_app.navigation.SimpleAppBar
import java.util.*

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddMovieScreen(navController: NavHostController, viewModel: MovieViewModel, modifier: Modifier = Modifier) {

    val isFormValid by viewModel.isFormValid.collectAsState(initial = false)

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                SimpleAppBar(title = "Add Movie") {
                    navController.popBackStack()
                }
            }

            var title by remember {
                mutableStateOf("Title")
            }

            var year by remember {
                mutableStateOf("2023")
            }

            val genres = Genre.values().toList()

            var genreItems by remember {
                mutableStateOf(
                    genres.map { genre ->
                        ListItemSelectable(
                            title = genre.toString(),
                            isSelected = false
                        )
                    }
                )
            }

            var director by remember {
                mutableStateOf("Director")
            }

            var actors by remember {
                mutableStateOf("Actors")
            }

            var plot by remember {
                mutableStateOf("Plot")
            }

            var rating by remember {
                mutableStateOf("9.9")
            }

            OutlinedTextField(
                title, { title = it }, Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text(text = stringResource(R.string.enter_movie_title)) },
                isError = false
            )

            OutlinedTextField(
                value = year,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { year = it },
                label = { Text(stringResource(R.string.enter_movie_year)) },
                isError = false
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.select_genres),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6)

            LazyRow {
                items(items = genreItems) { genreItem ->
                    Chip(
                        modifier = Modifier.padding(2.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (genreItem.isSelected)
                                colorResource(id = R.color.purple_200)
                            else
                                colorResource(id = R.color.white)
                        ),
                        onClick = {
                            genreItems = genreItems.map {
                                if (it.title == genreItem.title) {
                                    genreItem.copy(isSelected = !genreItem.isSelected)
                                } else {
                                    it
                                }
                            }
                        }
                    ) {
                        Text(text = genreItem.title)
                    }
                }
            }

            OutlinedTextField(
                value = director,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { director = it },
                label = { Text(stringResource(R.string.enter_director)) },
                isError = false
            )

            OutlinedTextField(
                value = actors,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { actors = it },
                label = { Text(stringResource(R.string.enter_actors)) },
                isError = false
            )

            OutlinedTextField(
                value = plot,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = { plot = it },
                label = { Text(textAlign = TextAlign.Start, text = stringResource(R.string.enter_plot)) },
                isError = false
            )

            OutlinedTextField(
                value = rating,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    rating = if(it.startsWith("0")) {
                        ""
                    } else {
                        it
                    }
                },
                label = { Text(stringResource(R.string.enter_rating)) },
                isError = false
            )

            LaunchedEffect(title, year, genreItems, director, actors, plot, rating) {
                viewModel.updateForm(
                    title = title,
                    year = year,
                    genres = genreItems.filter { it.isSelected }.map { it.title },
                    director = director,
                    actors = actors,
                    plot = plot,
                    rating = rating.toFloatOrNull()
                )
            }

            Button(
                enabled = isFormValid,
                onClick = {
                    val selectedGenres = genreItems.filter { it.isSelected }.map { it.title }
                    viewModel.addMovie(
                        Movie(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            year = year,
                            genres = selectedGenres.mapNotNull { genreTitle ->
                                Genre.values().firstOrNull { it.toString() == genreTitle }
                            },
                            director = director,
                            actors = actors,
                            plot = plot,
                            images = listOf(),
                            rating = rating.toFloatOrNull() ?: 0f
                        )
                    )
                    navController.popBackStack()
                }
            ) {
                Text(text = "Add Movie")
            }
        }
    }