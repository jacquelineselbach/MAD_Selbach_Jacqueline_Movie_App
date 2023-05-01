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

/**
 * DeleteAppBar is a composable function that renders a custom AppBar with a delete icon and a back icon.
 *
 * @param title the title of the AppBar.
 * @param onDeleteClick lambda function to be executed when the delete icon is clicked.
 * @param onBackClick lambda function to be executed when the back icon is clicked.
 * @param coroutineScope the CoroutineScope used for launching the onDeleteClick lambda in a coroutine.
 */

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

            // back icon with a click listener

            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {

            // delete icon with a click listener

            IconButton(onClick = {

                // launch the onDeleteClick lambda in a coroutine
                // is an extension function on CoroutineScope that starts a new coroutine
                // coroutine runs concurrently with other code - onDeleteClick() function inside the coroutine is executed asynchronously

                coroutineScope.launch {
                    onDeleteClick()
                }
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    )
}
