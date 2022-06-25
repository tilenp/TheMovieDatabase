package com.example.themoviedatabase.database.dao

import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.database.table.MovieGenreTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMovieGenreDao @Inject constructor(
    private val movieGenreDao: MovieGenreDao,
    private val uriIdlingResource: UriIdlingResource
): MovieGenreDao {

    override suspend fun insertMovieGenres(movieGenres: List<MovieGenreTable>) {
        return try {
            uriIdlingResource.beginLoad("insertMovieGenres")
            movieGenreDao.insertMovieGenres(movieGenres)
        } finally {
            uriIdlingResource.endLoad("insertMovieGenres")
        }
    }
}