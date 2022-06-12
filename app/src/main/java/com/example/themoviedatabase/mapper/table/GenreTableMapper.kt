package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.database.table.GenreTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.dto.GenreDTO
import com.example.themoviedatabase.model.dto.MovieDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreTableMapper @Inject constructor(): Mapper<MovieDTO, List<@JvmSuppressWildcards GenreTable>> {

    override fun map(objectToMap: MovieDTO): List<GenreTable> {
        return objectToMap.genres?.map { genreDto ->  mapGenre(genreDto) } ?: emptyList()
    }

    private fun mapGenre(genreDto: GenreDTO): GenreTable {
        return GenreTable(
            genreId = genreDto.id ?: 0L,
            name = genreDto.name.orEmpty()
        )
    }
}