package com.example.mad_movie_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mad_movie_app.components.MovieCard
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.models.DetailScreenViewModel
import com.example.mad_movie_app.models.SharedFavoriteViewModel
import com.example.mad_movie_app.navigation.DeleteAppBar
import com.example.mad_movie_app.ui.theme.bottomPadding
import com.example.mad_movie_app.ui.theme.horizontalPadding
import com.example.mad_movie_app.ui.theme.verticalPadding
import kotlinx.coroutines.launch

/**
 *
 * Composable function displaying the Detail Screen with information about the selected movie.
 *
 * @param navController the navigation controller used to navigate to other screens.
 * @param movie the selected [Movie] object.
 * @param detailScreenViewModel the [DetailScreenViewModel] used for displaying detail information as well as deleting a movie from the database.
 * @param viewModel the [SharedFavoriteViewModel] used to manage the favorite movies data.
 */

@Composable
fun DetailScreen(
    navController: NavHostController,
    movie: Movie?,
    detailScreenViewModel: DetailScreenViewModel,
    viewModel: SharedFavoriteViewModel
) {
    // remember whether the MovieCard is expanded or not
    val isExpanded = remember { mutableStateOf(false) }

    // observe whether the movie is favorite or not
    val isFavorite by remember(movie?.id) {
        derivedStateOf { viewModel.isFavoriteMovie(movie?.id ?: "") }
    }

    // remember a CoroutineScope to launch coroutines
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // display the DeleteAppBar with the movie title, a back button and a delete button
        DeleteAppBar(
            title = movie?.title ?: "Invalid selection",
            onDeleteClick = {
                coroutineScope.launch {
                    // launch a coroutine to delete the movie from the repository
                    detailScreenViewModel.deleteMovie(movie ?: return@launch)
                }
                navController.popBackStack()
            },
            onBackClick = { navController.popBackStack() },
            coroutineScope = coroutineScope
        )

        // if the movie is not null, display the movie information and images
        movie?.let {
            MovieCard(
                movie = it,
                isFavorite = isFavorite,
                onMovieClick = {},
                onFavoriteClick = {
                    viewModel.toggleFavorite(it.id)
                },
                isExpanded = isExpanded
            ) {
                // toggle the isExpanded state when the user taps the card
                isExpanded.value = !isExpanded.value
            }

            // display a divider and a header for the images
            Divider(modifier = Modifier.padding(verticalPadding))
            Text(
                text = "Movie Images",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(horizontalPadding)
                    .padding(bottomPadding)
            )

            // if the movie has more than one image, display them in a LazyRow
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
            // if the movie is null, display a message to the user
            Text(text = "Invalid movie selection")
        }
    }
}
