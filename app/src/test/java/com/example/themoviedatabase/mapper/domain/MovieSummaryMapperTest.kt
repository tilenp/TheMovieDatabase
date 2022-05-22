package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.utils.UIText
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieSummaryMapperTest {

    @Test
    fun map_movie_id_test() {
        // arrange
        val movieId = 1L
        val query = MovieSummaryQuery(movieId = movieId)
        val mapper = MovieSummaryMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(movieId, result.movieId)
    }

    @Test
    fun map_movie_title_test() {
        // arrange
        val title = "title"
        val query = MovieSummaryQuery(title = title)
        val mapper = MovieSummaryMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(UIText(string = title), result.title)
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