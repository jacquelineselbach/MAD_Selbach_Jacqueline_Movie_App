package com.example.mad_movie_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.mad_movie_app.data.Movie
import com.example.mad_movie_app.ui.theme.horizontalPadding
import com.example.mad_movie_app.ui.theme.verticalPadding
import com.example.mad_movie_app.ui.theme.weight

/**
 * A composable function that displays the details of a movie card.
 *
 * @param movie The Movie object containing the movie details.
 * @param isExpanded A boolean indicating if the movie card details are expanded or not.
 * @param onClick A lambda function to be executed when the expand/collapse icon is clicked.
 */

@Composable
fun MovieCardDetails(
    movie: Movie,
    isExpanded: Boolean,
    onClick: () -> Unit) {

    // create row to display the movie title and expand/collapse icon

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        Text(text = movie.title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(weight))
        Icon(
            imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
            contentDescription = "Expand",
            modifier = Modifier.clickable(onClick = onClick)
        )
    }

    // if card is expanded, display the movie details

    if (isExpanded) {

        Column(
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding)
        ) {
            Text(buildAnnotatedString("Director: ", movie.director))
            Text(buildAnnotatedString("Year: ", movie.year))
            Text(buildAnnotatedString("Genre: ", movie.genres.joinToString(", ") { it.toString() }))
            Text(buildAnnotatedString("Actors: ", movie.actors))
            Divider(modifier = Modifier.padding(vertical = verticalPadding))
            Text(
                text = "Plot:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = verticalPadding)
            )
            Text(
                text = movie.plot,
                modifier = Modifier.padding(top = verticalPadding, bottom = verticalPadding)
            )
        }
    }
}

/**
 * Builds an AnnotatedString with a bold label and regular value.
 *
 * @param label The label to be displayed in bold.
 * @param value The value to be displayed with regular font weight.
 * @return An AnnotatedString containing the label and value.
 */

@Composable
fun buildAnnotatedString(label: String, value: String): AnnotatedString {
    return AnnotatedString.Builder().apply {
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append(label)
        pop()
        append(value)
    }.toAnnotatedString()
}
