package com.example.mad_movie_app.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromGenresList(genres: List<Genre>): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenresList(genresString: String): List<Genre> {
        val type = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(genresString, type)
    }

    @TypeConverter
    fun fromImagesList(images: List<String>): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun toImagesList(imagesString: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(imagesString, type)
    }
}
