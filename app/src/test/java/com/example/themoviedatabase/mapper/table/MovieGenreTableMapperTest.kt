package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.database.table.MovieGenreTable
import com.example.themoviedatabase.model.dto.GenreDTO
import com.example.themoviedatabase.model.dto.MovieDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieGenreTableMapperTest {

    @Test
    fun map_movie_genres_test() {
        // arrange
        val movieId = 10L
        val id1 = 1L
        val id2 = 2L
        val genreDTOs = listOf(GenreDTO(id = id1), GenreDTO(id = id2))
        val movieDto = MovieDTO(id = movieId, genres = genreDTOs)
        val mapper = MovieGenreTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        val movieGenres = listOf(MovieGenreTable(movieId, id1), MovieGenreTable(movieId, id2))
        assertEquals(movieGenres, result)
    }

    @Test
    fun map_null_genre_id_test() {
        // arrange
        val movieId = 10L
        val genreDTOs = listOf(GenreDTO(id = null), GenreDTO(id = null))
        val movieDto = MovieDTO(id = movieId, genres = genreDTOs)
        val mapper = MovieGenreTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        val movieGenres = listOf(MovieGenreTable(movieId, 0), MovieGenreTable(movieId, 0))
        assertEquals(movieGenres, result)
    }
}