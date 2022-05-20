package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.dto.MovieDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieTableMapper @Inject constructor() : Mapper<MovieDTO, MovieTable> {

    override fun map(objectToMap: MovieDTO): MovieTable {
        return MovieTable(
            movieId = objectToMap.id,
            title = objectToMap.title.orEmpty(),
            popularity = objectToMap.popularity ?: 0f,
            overview = objectToMap.overview.orEmpty(),
            rating = objectToMap.voteAverage ?: 0f
        )
    }
}