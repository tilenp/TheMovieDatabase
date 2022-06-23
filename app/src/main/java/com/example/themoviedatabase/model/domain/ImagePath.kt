package com.example.themoviedatabase.model.domain

import androidx.annotation.DrawableRes
import androidx.annotation.VisibleForTesting
import com.example.themoviedatabase.BuildConfig.TMDB_IMAGE_URL

data class ImagePath(
    private val url: String = "",
    @DrawableRes val placeholder: Int = 0,
    @DrawableRes val resourceId: Int = 0
) {
    val medium = "$TMDB_IMAGE_URL$SIZE_500$url"

    companion object {
        @VisibleForTesting const val SIZE_500 = "/w500/"
    }
}
