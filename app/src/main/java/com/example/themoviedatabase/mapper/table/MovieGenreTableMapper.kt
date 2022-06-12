package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.database.table.MovieGenreTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.dto.MovieDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieGenreTableMapper @Inject constructor() : Mapper<MovieDTO, List<@JvmSuppressWildcards MovieGenreTable>> {

    override fun map(objectToMap: MovieDTO): List<MovieGenreTable> {
        return objectToMap.genres?.map { genreDto -> mapMovieGenre(objectToMap.id, genreDto.id) }
            ?: emptyList()
    }

    private fun mapMovieGenre(movieId: Long?, genreId: Long?): MovieGenreTable {
        return MovieGenreTable(
            movieId = movieId ?: 0L,
            genreId = genreId ?: 0L
        )
    }
}