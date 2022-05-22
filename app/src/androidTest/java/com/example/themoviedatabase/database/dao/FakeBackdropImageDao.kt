package com.example.themoviedatabase.database.dao

import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.database.table.BackdropImageTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeBackdropImageDao  @Inject constructor(
    private val backdropImageDao: BackdropImageDao,
    private val uriIdlingResource: UriIdlingResource
): BackdropImageDao {

    override suspend fun insertBackdropImages(backdropImages: List<BackdropImageTable>) {
        return try {
            uriIdlingResource.beginLoad("insertBackdropImages")
            backdropImageDao.insertBackdropImages(backdropImages)
        } finally {
            uriIdlingResource.endLoad("insertBackdropImages")
        }
    }

    override suspend fun deleteBackdropImages() {
        return try {
            uriIdlingResource.beginLoad("deleteBackdropImages")
            backdropImageDao.deleteBackdropImages()
        } finally {
            uriIdlingResource.endLoad("deleteBackdropImages")
        }
    }
}