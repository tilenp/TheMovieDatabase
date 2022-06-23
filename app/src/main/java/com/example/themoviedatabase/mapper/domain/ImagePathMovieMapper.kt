package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.R
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.ImagePath
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagePathMovieMapper @Inject constructor() : Mapper<String, ImagePath> {

    override fun map(objectToMap: String): ImagePath {
        return ImagePath(
            url = objectToMap,
            placeholder = R.drawable.ic_photo,
            resourceId = R.drawable.ic_broken_image
        )
    }
}