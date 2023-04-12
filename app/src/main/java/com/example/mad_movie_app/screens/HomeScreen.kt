package com.example.mad_movie_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mad_movie_app.models.Movie
import com.example.mad_movie_app.models.getMovies


@Composable
fun MovieList() {
    val movies = getMovies()
    LazyColumn {
        items(movies) { movie ->
            MovieRow(movie = movie)
        }
    }
}

@Composable
fun MovieRow(movie: Movie) {
    val expandedState = remember { mutableStateOf(false) }
    val isFavorite = remember { mutableStateOf(false) }

    Column {
        MoviePoster(
            posterUrl = movie.images.first(),
            isFavorite = isFavorite.value,
            onFavoriteClick = { isFavorite.value = !isFavorite.value }
        )
        MovieInformation(
            movie = movie,
            isExpanded = expandedState.value,
            onClick = { expandedState.value = !expandedState.value }
        )
    }
}

@Composable
fun MovieInformation(movie: Movie, isExpanded: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Text(text = movie.title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = movie.year)
        Icon(
            imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
            contentDescription = "Expand",
            modifier = Modifier.clickable(onClick = onClick)
        )
    }
    if (isExpanded) {
        Column(
            Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Text("Director: ${movie.director}")
            Text("Year: ${movie.year}")
            Text("Genre: ${movie.genre}")
            Text("Actors: ${movie.actors}")
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = "Plot:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = movie.plot,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
fun MoviePoster(posterUrl: String, isFavorite: Boolean, onFavoriteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(
                        data = posterUrl
                    ).apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()
                ),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier.align(alignment = Alignment.TopEnd)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite"
            )
        }
    }
}