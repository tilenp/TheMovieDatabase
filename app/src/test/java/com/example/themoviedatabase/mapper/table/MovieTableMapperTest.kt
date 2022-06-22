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
        assertEquals(id, result.movieId)
    }

    @Test
    fun map_title_test() {
        // arrange
        val title = "title"
        val movieDto = MovieDTO(title = title)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(title, result.title)
    }

    @Test
    fun map_null_title_test() {
        // arrange
        val movieDto = MovieDTO(title = null)
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
    fun map_rating_count_test() {
        // arrange
        val voteCount = 1L
        val movieDto = MovieDTO(voteCount = voteCount)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(voteCount, result.ratingCount)
    }

    @Test
    fun map_null_rating_count_test() {
        // arrange
        val movieDto = MovieDTO(voteCount = null)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(0L, result.ratingCount)
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

    @Test
    fun map_release_date_test() {
        // arrange
        val releaseDate = "releaseDate"
        val movieDto = MovieDTO(releaseDate = releaseDate)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(releaseDate, result.releaseDate)
    }

    @Test
    fun map_null_release_date_test() {
        // arrange
        val movieDto = MovieDTO(releaseDate = null)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals("", result.releaseDate)
    }

    @Test
    fun map_runtime_test() {
        // arrange
        val runtime = 82
        val movieDto = MovieDTO(runtime = runtime)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(runtime, result.runtime)
    }

    @Test
    fun map_null_runtime_test() {
        // arrange
        val movieDto = MovieDTO(runtime = null)
        val mapper = MovieTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(0, result.runtime)
    }
}