package com.example.mad_movie_app.data

/**
 * Enum class representing the various movie genres available.
 * Each genre value is mapped to its corresponding human-readable string representation.
 */

enum class Genre {
    ACTION,
    ADVENTURE,
    ANIMATION,
    BIOGRAPHY,
    COMEDY,
    CRIME,
    DOCUMENTARY,
    DRAMA,
    FAMILY,
    FANTASY,
    FILM_NOIR,
    HISTORY,
    HORROR,
    MUSIC,
    MUSICAL,
    MYSTERY,
    ROMANCE,
    SCI_FI,
    SPORT,
    THRILLER,
    WAR,
    WESTERN;

    /**
     * Overrides the default toString() method to return the human-readable string representation
     * of the genre value.
     *
     * @return The string representation of the genre value.
     */

    override fun toString(): String {
        return when (this) {
            ACTION -> "Action"
            ADVENTURE -> "Adventure"
            ANIMATION -> "Animation"
            BIOGRAPHY -> "Biography"
            COMEDY -> "Comedy"
            CRIME -> "Crime"
            DOCUMENTARY -> "Documentary"
            DRAMA -> "Drama"
            FAMILY -> "Family"
            FANTASY -> "Fantasy"
            FILM_NOIR -> "Film Noir"
            HISTORY -> "History"
            HORROR -> "Horror"
            MUSIC -> "Music"
            MUSICAL -> "Musical"
            MYSTERY -> "Mystery"
            ROMANCE -> "Romance"
            SCI_FI -> "Sci-Fi"
            SPORT -> "Sport"
            THRILLER -> "Thriller"
            WAR -> "War"
            WESTERN -> "Western"
        }
    }
}
