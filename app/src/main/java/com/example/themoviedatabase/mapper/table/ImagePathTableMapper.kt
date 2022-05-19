package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.dto.MovieDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagePathTableMapper @Inject constructor(): Mapper<MovieDTO, ImagePathTable> {

    override fun map(objectToMap: MovieDTO): ImagePathTable {
        return ImagePathTable(
            path = objectToMap.posterPath.orEmpty(),
            itemId = objectToMap.id
        )
    }
}