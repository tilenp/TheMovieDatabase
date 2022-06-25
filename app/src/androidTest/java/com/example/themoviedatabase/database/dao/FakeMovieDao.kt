package com.example.themoviedatabase.database.dao

import androidx.paging.PagingSource
import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.MovieTable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMovieDao @Inject constructor(
    private val movieDao: MovieDao,
    private val uriIdlingResource: UriIdlingResource
) : MovieDao() {

    override suspend fun insertMovies(movies: List<MovieTable>) {
        return try {
            uriIdlingResource.beginLoad("insertMovies")
            movieDao.insertMovies(movies)
        } finally {
            uriIdlingResource.endLoad("insertMovies")
        }
    }

    override fun getPopularMovies(): PagingSource<Int, MovieSummaryQuery> {
        return try {
            uriIdlingResource.beginLoad("getPopularMovies")
            movieDao.getPopularMovies()
        } finally {
            uriIdlingResource.endLoad("getPopularMovies")
        }
    }

    override fun getSimilarMovies(movieId: Long): Flow<List<MovieSummaryQuery>> {
        return try {
            uriIdlingResource.beginLoad("getSimilarMovies")
            movieDao.getSimilarMovies(movieId)
        } finally {
            uriIdlingResource.endLoad("getSimilarMovies")
        }
    }

    override suspend fun deleteMovies() {
        return try {
            uriIdlingResource.beginLoad("deleteMovies")
            movieDao.deleteMovies()
        } finally {
            uriIdlingResource.endLoad("deleteMovies")
        }
    }

    override suspend fun updateMovie(
        title: String,
        popularity: Float,
        overview: String,
        ratingCount: Long,
        rating: Float,
        releaseDate: String,
        runtime: Int,
        movieId: Long
    ) {
        return try {
            uriIdlingResource.beginLoad("updateMovie")
            movieDao.updateMovie(
                title = title,
                popularity = popularity,
                overview = overview,
                ratingCount = ratingCount,
                rating = rating,
                releaseDate = releaseDate,
                runtime = runtime,
                movieId = movieId
            )
        } finally {
            uriIdlingResource.endLoad("updateMovie")
        }
    }

    override suspend fun insertMovie(movie: MovieTable): Long {
        return try {
            uriIdlingResource.beginLoad("insertMovie")
            movieDao.insertMovie(movie)
        } finally {
            uriIdlingResource.endLoad("insertMovie")
        }
    }

    override fun getMovieDetailsWithId(movieId: Long): Flow<MovieDetailsQuery> {
        return try {
            uriIdlingResource.beginLoad("getMovieDetailsWithId")
            movieDao.getMovieDetailsWithId(movieId)
        } finally {
            uriIdlingResource.endLoad("getMovieDetailsWithId")
        }
    }
}