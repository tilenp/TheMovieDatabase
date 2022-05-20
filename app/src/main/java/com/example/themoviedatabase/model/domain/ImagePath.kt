package com.example.themoviedatabase.model.domain

import androidx.annotation.DrawableRes
import com.example.themoviedatabase.utils.IMAGE_URL
import com.example.themoviedatabase.utils.MEDIUM

data class ImagePath(
    private val url: String = "",
    @DrawableRes val placeholder: Int = 0,
    @DrawableRes val backup: Int = 0
) {
    val medium = "$IMAGE_URL$MEDIUM$url"
}
