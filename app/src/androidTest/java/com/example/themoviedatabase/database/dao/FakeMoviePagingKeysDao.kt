package com.example.themoviedatabase.database.dao

import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.database.table.MoviePagingKeysTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMoviePagingKeysDao @Inject constructor(
    private val moviePagingKeysDao: MoviePagingKeysDao,
    private val uriIdlingResource: UriIdlingResource
) : MoviePagingKeysDao {

    override suspend fun insertPagingKeys(pagingKeys: List<MoviePagingKeysTable>) {
        return try {
            uriIdlingResource.beginLoad("insertPagingKeys")
            moviePagingKeysDao.insertPagingKeys(pagingKeys)
        } finally {
            uriIdlingResource.endLoad("insertPagingKeys")
        }
    }

    override suspend fun getPagingKeysForMovieId(movieId: Long): MoviePagingKeysTable? {
        return try {
            uriIdlingResource.beginLoad("getPagingKeysForMovieId")
            moviePagingKeysDao.getPagingKeysForMovieId(movieId)
        } finally {
            uriIdlingResource.endLoad("getPagingKeysForMovieId")
        }
    }
}