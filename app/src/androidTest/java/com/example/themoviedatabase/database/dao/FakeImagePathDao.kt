package com.example.themoviedatabase.database.dao

import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.database.table.ImagePathTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeImagePathDao @Inject constructor(
    private val imagePathDao: ImagePathDao,
    private val uriIdlingResource: UriIdlingResource
): ImagePathDao {

    override suspend fun insertImagePaths(imagePaths: List<ImagePathTable>) {
        return try {
            uriIdlingResource.beginLoad("insertImagePaths")
            imagePathDao.insertImagePaths(imagePaths)
        } finally {
            uriIdlingResource.endLoad("insertImagePaths")
        }
    }

    override suspend fun deleteImagePaths() {
        return try {
            uriIdlingResource.beginLoad("deleteImagePaths")
            imagePathDao.deleteImagePaths()
        } finally {
            uriIdlingResource.endLoad("deleteImagePaths")
        }
    }
}