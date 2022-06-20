package com.example.themoviedatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.themoviedatabase.database.dao.*
import com.example.themoviedatabase.database.table.*

@Database(
    entities = [
        BackdropImageTable::class,
        GenreTable::class,
        ImagePathTable::class,
        MovieGenreTable::class,
        MovieTable::class,
        MoviePagingKeysTable::class,
        SimilarMovieTable::class,
        VideoTable::class,
    ],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getBackdropImageDao(): BackdropImageDao
    abstract fun getGenreDao(): GenreDao
    abstract fun getImagePathDao(): ImagePathDao
    abstract fun getMovieGenreDao(): MovieGenreDao
    abstract fun getMovieDao(): MovieDao
    abstract fun getMoviePagingKeysDao(): MoviePagingKeysDao
    abstract fun getSimilarMoviesDao(): SimilarMoviesDao
    abstract fun getVideoDao(): VideoDao

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, MovieDatabase::class.java, "MOVIE_DATABASE")
                .build()
    }
}