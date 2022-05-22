package com.example.themoviedatabase.model.domain

import androidx.annotation.DrawableRes
import com.example.themoviedatabase.BuildConfig.TMDB_IMAGE_SIZE_500
import com.example.themoviedatabase.BuildConfig.TMDB_IMAGE_URL

data class ImagePath(
    private val url: String = "",
    @DrawableRes val placeholder: Int = 0,
    @DrawableRes val backup: Int = 0
) {
    val medium = "$TMDB_IMAGE_URL$TMDB_IMAGE_SIZE_500$url"
}
