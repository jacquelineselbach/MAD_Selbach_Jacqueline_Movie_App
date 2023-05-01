package com.example.mad_movie_app.navigation

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

/**
 * SimpleAppBar composable function creates a custom simple app bar with a back button.
 *
 * @param title the title text to display in the app bar.
 * @param onBackClick the lambda function to execute when the back button is clicked.
 */

@Composable
fun SimpleAppBar(title: String, onBackClick: () -> Unit) {

    TopAppBar(
        title = { Text(text = title) },

        // add back button as the navigation icon with a click listener

        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}
