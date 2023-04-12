package com.example.mad_movie_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mad_movie_app.components.MovieCard
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.models.MovieViewModel
import com.example.mad_movie_app.navigation.SimpleAppBar
import com.example.mad_movie_app.ui.theme.bottomPadding
import com.example.mad_movie_app.ui.theme.horizontalPadding
import com.example.mad_movie_app.ui.theme.verticalPadding

@Composable
fun DetailScreen(
    navController: NavHostController,
    movie: Movie?,
    viewModel: MovieViewModel
) {
    val isExpanded = remember { mutableStateOf(false) }
    val isFavorite = rememberSaveable { mutableStateOf(viewModel.isFavoriteMovie(movie?.id ?: "")) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SimpleAppBar(title = movie?.title ?: "Invalid selection") {
            navController.popBackStack()
        }

        movie?.let {
            MovieCard(
                movie = it,
                isFavorite = isFavorite.value,
                onMovieClick = {},
                onFavoriteClick = {
                    viewModel.toggleFavorite(movie.id)
                    isFavorite.value = viewModel.isFavoriteMovie(movie.id)
                },
                isExpanded = isExpanded
            ) { isExpanded.value = !isExpanded.value }

            Divider(modifier = Modifier.padding(verticalPadding))
            Text(
                text = "Movie Images",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(horizontalPadding)
                    .padding(bottomPadding)
            )

            if (it.images.size > 1) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontalPadding)
                ) {
                    items(it.images.subList(1, it.images.size)) { imageUrl ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = 4.dp,
                            modifier = Modifier
                                .width(400.dp)
                                .height(250.dp)
                                .padding(end = 16.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current).data(
                                        data = imageUrl
                                    ).apply(block = fun ImageRequest.Builder.() {
                                        crossfade(true)
                                    }).build()
                                ),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        } ?: run {
            Text(text = "Invalid movie selection")
        }
    }
}