package com.example.themoviedatabase.database.dao

import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.database.table.SimilarMovieTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeSimilarMoviesDao @Inject constructor(
    private val similarMoviesDao: SimilarMoviesDao,
    private val uriIdlingResource: UriIdlingResource
): SimilarMoviesDao {

    override suspend fun insertSimilarMovies(similarMovies: List<SimilarMovieTable>) {
        return try {
            uriIdlingResource.beginLoad("insertSimilarMovies")
            similarMoviesDao.insertSimilarMovies(similarMovies)
        } finally {
            uriIdlingResource.endLoad("insertSimilarMovies")
        }
    }
}