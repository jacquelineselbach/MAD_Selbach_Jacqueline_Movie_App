package com.example.mad_movie_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun MoviePoster(
    posterUrl: String,
    onFavoriteClick: (String) -> Unit,
    onMovieClick: () -> Unit
) {
    var isFavorite: Boolean by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context).data(data = posterUrl).apply {
            crossfade(true)
        }.build()
    )
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            elevation = 4.dp,
            modifier = Modifier.fillMaxSize()
                .clickable { onMovieClick() }
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        IconButton(
            onClick = {
                isFavorite = !isFavorite
                onFavoriteClick(posterUrl)
            },
            modifier = Modifier.align(alignment = Alignment.TopEnd)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
            )
        }
    }
}