package com.example.themoviedatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.themoviedatabase.database.dao.BackdropImageDao
import com.example.themoviedatabase.database.dao.ImagePathDao
import com.example.themoviedatabase.database.dao.MovieDao
import com.example.themoviedatabase.database.dao.MoviePagingKeysDao
import com.example.themoviedatabase.database.table.BackdropImageTable
import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.database.table.MoviePagingKeysTable
import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.utils.DATABASE_NAME

@Database(
    entities = [
        BackdropImageTable::class,
        ImagePathTable::class,
        MovieTable::class,
        MoviePagingKeysTable::class
    ], version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getBackdropImageDao(): BackdropImageDao
    abstract fun getImagePathDao(): ImagePathDao
    abstract fun getMovieDao(): MovieDao
    abstract fun getMoviePagingKeysDao(): MoviePagingKeysDao

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, MovieDatabase::class.java, DATABASE_NAME)
                .build()
    }
}