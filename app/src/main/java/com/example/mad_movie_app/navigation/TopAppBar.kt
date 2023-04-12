package com.example.mad_movie_app.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

@Composable
fun TopBar(onFavoriteClick: () -> Unit, onAddClick: () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("Movies") },
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Show menu")
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(onClick = onFavoriteClick) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                        Text(" Favorites")
                    }
                }
                DropdownMenuItem(onClick = onAddClick) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Add, contentDescription = "Add movie")
                        Text(" Add movie")
                    }
                }
            }
        }
    )
}