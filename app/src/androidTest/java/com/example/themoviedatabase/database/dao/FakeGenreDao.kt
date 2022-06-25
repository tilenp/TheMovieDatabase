package com.example.themoviedatabase.database.dao

import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.database.table.GenreTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeGenreDao @Inject constructor(
    private val genreDao: GenreDao,
    private val uriIdlingResource: UriIdlingResource
): GenreDao() {

    override suspend fun insertGenre(genre: GenreTable): Long {
        return try {
            uriIdlingResource.beginLoad("insertGenre")
            genreDao.insertGenre(genre)
        } finally {
            uriIdlingResource.endLoad("insertGenre")
        }
    }

    override suspend fun updateGenre(genre: GenreTable) {
        return try {
            uriIdlingResource.beginLoad("updateGenres")
            genreDao.updateGenre(genre)
        } finally {
            uriIdlingResource.endLoad("updateGenres")
        }
    }
}