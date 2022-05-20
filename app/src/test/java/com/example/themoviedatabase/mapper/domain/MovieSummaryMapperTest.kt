package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.utils.UIText
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieSummaryMapperTest {

    @Test
    fun map_id_test() {
        // arrange
        val id = 1L
        val movieTable = MovieTable(id = id)
        val query = MovieSummaryQuery(movieTable = movieTable)
        val mapper = MovieSummaryMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(id, result.id)
    }

    @Test
    fun map_movie_title_test() {
        // arrange
        val title = "title"
        val movieTable = MovieTable(title = title)
        val query = MovieSummaryQuery(movieTable = movieTable)
        val mapper = MovieSummaryMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(UIText(string = title), result.title)
    }

    @Test
    fun map_movie_popularity_test() {
        // arrange
        val popularity = 1f
        val movieTable = MovieTable(popularity = popularity)
        val query = MovieSummaryQuery(movieTable = movieTable)
        val mapper = MovieSummaryMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(popularity, result.popularity)
    }

    @Test
    fun map_movie_overview_test() {
        // arrange
        val overview = "overview"
        val movieTable = MovieTable(overview = overview)
        val query = MovieSummaryQuery(movieTable = movieTable)
        val mapper = MovieSummaryMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(UIText(string = overview), result.overview)
    }

    @Test
    fun map_movie_posterPath_test() {
        // arrange
        val imagePath = ImagePath("path", 0, 0)
        val imagePathMovieMapper: ImagePathMovieMapper = mockk()
        every { imagePathMovieMapper.map(any()) } returns imagePath

        val query = MovieSummaryQuery()
        val mapper = MovieSummaryMapper(imagePathMovieMapper)

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(imagePath, result.posterPath)
    }
}