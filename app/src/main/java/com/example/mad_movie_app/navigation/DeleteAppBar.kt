package com.example.mad_movie_app.navigation

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DeleteAppBar(
    title: String,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    coroutineScope: CoroutineScope
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = {
                coroutineScope.launch {
                    onDeleteClick()
                }
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    )
}
