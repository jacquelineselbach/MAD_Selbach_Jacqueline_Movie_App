package com.example.mad_movie_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * MovieDatabase is an abstract class that represents the Room database for the app.
 * It extends RoomDatabase and provides a singleton instance for the database.
 *
 * The database contains a single table for the Movie entity and uses a custom TypeConverter for data conversion.
 */

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    /**
     * Provides access to MovieDao, which is used for performing database operations
     *
     * @return A MovieDao instance.
     */

    abstract fun movieDao(): MovieDao

    companion object {

        // singleton instance of the MovieDatabase

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        /**
         * Returns the singleton instance of the MovieDatabase.
         * If the instance is not initialized yet, it creates and initializes a new instance.
         *
         * @param context The application context.
         * @return The singleton instance of the MovieDatabase.
         */

        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
