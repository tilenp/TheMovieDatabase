package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.utils.UIText
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailsMapperTest {

    @Test
    fun map_movie_id_test() {
        // arrange
        val movieId = 1L
        val query = MovieDetailsQuery(MovieTable(movieId = movieId))
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(movieId, result.movieId)
    }

    @Test
    fun map_title_test() {
        // arrange
        val title = "title"
        val query = MovieDetailsQuery(MovieTable(title = title))
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(UIText(string = title), result.title)
    }

    @Test
    fun map_backdrop_test() {
        // arrange
        val imagePath = ImagePath(url = "path")
        val imagePathMovieMapper: ImagePathMovieMapper = mockk()
        every { imagePathMovieMapper.map(any()) } returns imagePath

        val query = MovieDetailsQuery()
        val mapper = MovieDetailsMapper(imagePathMovieMapper)

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(imagePath, result.backdropPath)
    }

    @Test
    fun map_rating_test() {
        // arrange
        val rating = 10.0f
        val query = MovieDetailsQuery(MovieTable(rating = rating))
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(rating, result.rating)
    }

    @Test
    fun map_overview_test() {
        // arrange
        val overview = "overview"
        val query = MovieDetailsQuery(MovieTable(overview = overview))
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(UIText(string = overview), result.overview)
    }
}