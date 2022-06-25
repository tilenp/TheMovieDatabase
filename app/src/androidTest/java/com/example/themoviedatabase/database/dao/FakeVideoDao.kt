package com.example.themoviedatabase.database.dao

import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.database.table.VideoTable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeVideoDao @Inject constructor(
    private val videoDao: VideoDao,
    private val uriIdlingResource: UriIdlingResource
): VideoDao {

    override suspend fun insertVideos(videos: List<VideoTable>) {
        return try {
            uriIdlingResource.beginLoad("insertVideos")
            videoDao.insertVideos(videos)
        } finally {
            uriIdlingResource.endLoad("insertVideos")
        }
    }

    override fun getVideosWithItemId(itemId: Long): Flow<List<VideoTable>> {
        return try {
            uriIdlingResource.beginLoad("getVideosWithItemId")
            videoDao.getVideosWithItemId(itemId)
        } finally {
            uriIdlingResource.endLoad("getVideosWithItemId")
        }
    }
}