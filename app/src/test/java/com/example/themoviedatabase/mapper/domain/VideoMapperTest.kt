package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.BuildConfig
import com.example.themoviedatabase.BuildConfig.YOUTUBE_THUMBNAIL_URL
import com.example.themoviedatabase.database.table.VideoTable
import com.example.themoviedatabase.model.domain.Video.Companion.THUMBNAIL_SIZE_MQ
import org.junit.Assert.assertEquals
import org.junit.Test

class VideoMapperTest {

    @Test
    fun map_thumbnail_MQ_test() {
        // arrange
        val key = "key"
        val mapper = VideoMapper()

        // act
        val result = mapper.map(VideoTable(key = key))

        // assert
        val thumbnail = "${YOUTUBE_THUMBNAIL_URL}$key/${THUMBNAIL_SIZE_MQ}default.jpg"
        assertEquals(thumbnail, result.thumbnailMQ)
    }

    @Test
    fun map_video_url_test() {
        // arrange
        val key = "key"
        val mapper = VideoMapper()

        // act
        val result = mapper.map(VideoTable(key = key))

        // assert
        val videoUrl = "${BuildConfig.YOUTUBE_VIDEO_URL}$key"
        assertEquals(videoUrl, result.videoUrl)
    }
}