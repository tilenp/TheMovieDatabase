package com.example.themoviedatabase.database.dao

import androidx.paging.PagingSource
import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.MovieTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMovieDao @Inject constructor(
    private val movieDao: MovieDao,
    private val uriIdlingResource: UriIdlingResource
) : MovieDao {

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

    override suspend fun deleteMovies() {
        return try {
            uriIdlingResource.beginLoad("deleteMovies")
            movieDao.deleteMovies()
        } finally {
            uriIdlingResource.endLoad("deleteMovies")
        }
    }
}