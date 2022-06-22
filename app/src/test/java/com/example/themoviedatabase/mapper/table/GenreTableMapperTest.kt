package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.database.table.GenreTable
import com.example.themoviedatabase.model.dto.GenreDTO
import com.example.themoviedatabase.model.dto.MovieDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class GenreTableMapperTest {

    @Test
    fun map_genres_test() {
        // arrange
        val id1 = 1L
        val id2 = 2L
        val name1 = "genre1"
        val name2 = "genre2"
        val genreDTOs = listOf(GenreDTO(id1, name1), GenreDTO(id2, name2))
        val movieDto = MovieDTO(genres = genreDTOs)
        val mapper = GenreTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        val genres = listOf(GenreTable(id1, name1), GenreTable(id2, name2))
        assertEquals(genres, result)
    }

    @Test
    fun map_null_id_test() {
        // arrange
        val genreDTOs = listOf(GenreDTO())
        val movieDto = MovieDTO(genres = genreDTOs)
        val mapper = GenreTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        val genres = listOf(GenreTable(genreId = 0))
        assertEquals(genres, result)
    }

    @Test
    fun map_null_name_test() {
        // arrange
        val genreDTOs = listOf(GenreDTO())
        val movieDto = MovieDTO(genres = genreDTOs)
        val mapper = GenreTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        val genres = listOf(GenreTable(name = ""))
        assertEquals(genres, result)
    }
}