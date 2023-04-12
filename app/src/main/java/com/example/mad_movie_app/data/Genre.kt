package com.example.mad_movie_app.data

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
