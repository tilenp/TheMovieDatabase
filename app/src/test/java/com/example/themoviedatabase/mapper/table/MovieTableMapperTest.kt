package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.model.dto.MovieDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieTableMapperTest {

    @Test
    fun map_id_test() {
        // arrange
        val id = 1L
        val movieDto = MovieDTO(id = id)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(id, result.id)
    }

    @Test
    fun map_name_test() {
        // arrange
        val name = "name"
        val movieDto = MovieDTO(name = name)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(name, result.title)
    }

    @Test
    fun map_null_name_test() {
        // arrange
        val movieDto = MovieDTO(name = null)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals("", result.title)
    }

    @Test
    fun map_popularity_test() {
        // arrange
        val popularity = 1f
        val movieDto = MovieDTO(popularity = popularity)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(popularity, result.popularity)
    }

    @Test
    fun map_null_popularity_test() {
        // arrange
        val movieDto = MovieDTO(popularity = null)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(0f, result.popularity)
    }

    @Test
    fun map_overview_test() {
        // arrange
        val overview = "overview"
        val movieDto = MovieDTO(overview = overview)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(overview, result.overview)
    }

    @Test
    fun map_null_overview_test() {
        // arrange
        val movieDto = MovieDTO(overview = null)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals("", result.overview)
    }

    @Test
    fun map_rating_test() {
        // arrange
        val voteAverage = 1f
        val movieDto = MovieDTO(voteAverage = voteAverage)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(voteAverage, result.rating)
    }

    @Test
    fun map_null_rating_test() {
        // arrange
        val movieDto = MovieDTO(voteAverage = null)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(0f, result.popularity)
    }
}