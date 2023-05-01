package com.example.mad_movie_app.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

/**
 * TopBar composable function creates a top app bar with a dropdown menu containing favorites and add movie actions.
 *
 * @param onFavoriteClick The lambda function to execute when the favorite menu item is clicked.
 * @param onAddClick The lambda function to execute when the add movie menu item is clicked.
 */

@Composable
fun TopBar(onFavoriteClick: () -> Unit, onAddClick: () -> Unit) {

    // declare and initialize mutable state to control visibility of the dropdown menu

    var showMenu by remember { mutableStateOf(false) }

    // create a TopAppBar with title and actions

    TopAppBar(
        title = { Text("Movies") },
        actions = {

            // add IconButton with moreVert icon to toggle the dropdown menu

            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Show menu")
            }

            // create DropdownMenu with two menu items: Favorites and Add Movie

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {

                // add DropdownMenuItem for Favorites action

                DropdownMenuItem(onClick = onFavoriteClick) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                        Text(" Favorites")
                    }
                }

                // add DropdownMenuItem for Add Movie action

                DropdownMenuItem(onClick = onAddClick) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Add, contentDescription = "Add Movie")
                        Text(" Add Movie")
                    }
                }
            }
        }
    )
}
