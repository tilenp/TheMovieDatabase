package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.R
import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.ImagePath
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagePathMovieMapper @Inject constructor() : Mapper<ImagePathTable, ImagePath> {

    override fun map(objectToMap: ImagePathTable): ImagePath {
        return ImagePath(
            url = objectToMap.path,
            placeholder = R.drawable.ic_photo,
            backup = R.drawable.ic_broken_image
        )
    }
}