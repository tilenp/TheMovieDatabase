package com.example.themoviedatabase.model.domain

import androidx.annotation.VisibleForTesting
import com.example.themoviedatabase.BuildConfig.YOUTUBE_THUMBNAIL_URL
import com.example.themoviedatabase.BuildConfig.YOUTUBE_VIDEO_URL

data class Video(
    private val id: String = "",
    private val key: String = ""
) {
    val thumbnailMQ = "${YOUTUBE_THUMBNAIL_URL}$key/${THUMBNAIL_SIZE_MQ}default.jpg"
    val videoUrl = "${YOUTUBE_VIDEO_URL}$key"

    companion object {
        @VisibleForTesting const val THUMBNAIL_SIZE_MQ = "mq"
    }
}