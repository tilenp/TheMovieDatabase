package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.database.table.BackdropImageTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.dto.MovieDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackdropImageTableMapper @Inject constructor(): Mapper<MovieDTO, BackdropImageTable> {

    override fun map(objectToMap: MovieDTO): BackdropImageTable {
        return BackdropImageTable(
            path = objectToMap.backdropPath.orEmpty(),
            itemId = objectToMap.id
        )
    }
}