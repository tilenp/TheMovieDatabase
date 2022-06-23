package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.R
import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.database.table.GenreTable
import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.mapper.domain.MovieDetailsMapper.Companion.PLACEHOLDER
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.utils.UIText
import com.example.themoviedatabase.utils.thousandFormat
import com.example.themoviedatabase.utils.toHourMinutes
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
        assertEquals(title, result.title.string)
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
        val rating = 10.01f
        val formattedRating = "10.0"
        val query = MovieDetailsQuery(MovieTable(rating = rating))
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(rating, result.rating.value)
        assertEquals(formattedRating, result.rating.formattedValue)
    }

    @Test
    fun map_rating_count_test() {
        // arrange
        val ratingCount = 1000L
        val query = MovieDetailsQuery(MovieTable(ratingCount = ratingCount))
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(R.plurals.rating_count_format, result.ratingCount.pluralId)
        assertEquals(ratingCount.thousandFormat(), result.ratingCount.formatArgs)
        assertEquals(ratingCount, result.ratingCount.count)
    }

    @Test
    fun map_genres_test() {
        // arrange
        val genre1 = "genre1"
        val genre2 = "genre2"
        val genres = listOf(GenreTable(name = genre1), GenreTable(name = genre2))
        val query = MovieDetailsQuery(genres = genres)
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals("$genre1, $genre2", result.genres)
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
        assertEquals(overview, result.overview.string)
    }

    @Test
    fun map_release_date_test() {
        // arrange
        val releaseDate = "release date"
        val query = MovieDetailsQuery(MovieTable(releaseDate = releaseDate))
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(releaseDate, result.releaseDate)
    }

    @Test
    fun map_runtime_icon_test() {
        // arrange
        val runtimeIcon = R.drawable.clock
        val query = MovieDetailsQuery()
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(runtimeIcon, result.runtimeIcon.resourceId)
    }

    @Test
    fun map_runtime_test() {
        // arrange
        val runtime = 82
        val query = MovieDetailsQuery(MovieTable(runtime = runtime))
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(runtime.toHourMinutes(), result.runtime)
    }

    @Test
    fun map_empty_runtime_test() {
        // arrange
        val runtime = 0
        val query = MovieDetailsQuery(MovieTable(runtime = runtime))
        val mapper = MovieDetailsMapper(ImagePathMovieMapper())

        // act
        val result = mapper.map(query)

        // assert
        assertEquals(PLACEHOLDER, result.runtime)
    }
}