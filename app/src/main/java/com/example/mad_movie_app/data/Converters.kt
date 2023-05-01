package com.example.mad_movie_app.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * This class contains a set of type converters for Room database.
 * It provides methods to convert custom data types (genres, images) to and from
 * their equivalent string representation, allowing storage in the database.
 */

class Converters {

    /**
     * Converts a list of genres to its JSON string representation.
     *
     * @param genres The list of genres to be converted.
     * @return The JSON string representation of the list of genres.
     */

    @TypeConverter
    fun fromGenresList(genres: List<Genre>): String {
        return Gson().toJson(genres)
    }

    /**
     * Converts a JSON string representation of a list of genres back to a list of genres.
     *
     * @param genresString The JSON string representation of the list of genres.
     * @return The list of genres converted from the JSON string.
     */

    @TypeConverter
    fun toGenresList(genresString: String): List<Genre> {
        val type = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(genresString, type)
    }

    /**
     * Converts a list of image URLs to its JSON string representation.
     *
     * @param images The list of image URLs to be converted.
     * @return The JSON string representation of the list of image URLs.
     */

    @TypeConverter
    fun fromImagesList(images: List<String>): String {
        return Gson().toJson(images)
    }

    /**
     * Converts a JSON string representation of a list of image URLs back to a list of image URLs.
     *
     * @param imagesString The JSON string representation of the list of image URLs.
     * @return The list of image URLs converted from the JSON string.
     */

    @TypeConverter
    fun toImagesList(imagesString: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(imagesString, type)
    }
}
