package com.example.themoviedatabase.dagger.module

import android.content.Context
import androidx.room.Room
import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.database.MovieDatabase
import com.example.themoviedatabase.database.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FakeDatabaseModule {

    @Singleton
    @Provides
    fun providesMovieDatabase(context: Context): MovieDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            MovieDatabase::class.java,
        )
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun providesBackdropImageDao(
        database: MovieDatabase,
        uriIdlingResource: UriIdlingResource
    ): BackdropImageDao {
        return FakeBackdropImageDao(database.getBackdropImageDao(), uriIdlingResource)
    }

    @Singleton
    @Provides
    fun providesGenreDao(database: MovieDatabase): GenreDao {
        return database.getGenreDao()
    }

    @Singleton
    @Provides
    fun providesImagePathDao(
        database: MovieDatabase,
        uriIdlingResource: UriIdlingResource
    ): ImagePathDao {
        return FakeImagePathDao(database.getImagePathDao(), uriIdlingResource)
    }

    @Singleton
    @Provides
    fun providesMovieDao(database: MovieDatabase): MovieDao {
        return database.getMovieDao()
    }

    @Singleton
    @Provides
    fun providesMovieGenreDao(database: MovieDatabase): MovieGenreDao {
        return database.getMovieGenreDao()
    }

    @Singleton
    @Provides
    fun providesMoviePagingKeysDao(
        database: MovieDatabase,
        uriIdlingResource: UriIdlingResource
    ): MoviePagingKeysDao {
        return FakeMoviePagingKeysDao(database.getMoviePagingKeysDao(), uriIdlingResource)
    }

    @Singleton
    @Provides
    fun providesSimilarMoviesDao(database: MovieDatabase): SimilarMoviesDao {
        return database.getSimilarMoviesDao()
    }

    @Singleton
    @Provides
    fun providesVideoDao(database: MovieDatabase): VideoDao {
        return database.getVideoDao()
    }
}