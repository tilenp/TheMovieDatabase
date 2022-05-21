package com.example.themoviedatabase.dagger.module

import android.content.Context
import com.example.themoviedatabase.database.MovieDatabase
import com.example.themoviedatabase.database.dao.BackdropImageDao
import com.example.themoviedatabase.database.dao.ImagePathDao
import com.example.themoviedatabase.database.dao.MovieDao
import com.example.themoviedatabase.database.dao.MoviePagingKeysDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesMovieDatabase(context: Context): MovieDatabase {
        return MovieDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun providesBackdropImageDao(database: MovieDatabase): BackdropImageDao {
        return database.getBackdropImageDao()
    }

    @Singleton
    @Provides
    fun providesImagePathDao(database: MovieDatabase): ImagePathDao {
        return database.getImagePathDao()
    }

    @Singleton
    @Provides
    fun providesMovieDao(database: MovieDatabase): MovieDao {
        return database.getMovieDao()
    }

    @Singleton
    @Provides
    fun providesMoviePagingKeysDao(database: MovieDatabase): MoviePagingKeysDao {
        return database.getMoviePagingKeysDao()
    }
}