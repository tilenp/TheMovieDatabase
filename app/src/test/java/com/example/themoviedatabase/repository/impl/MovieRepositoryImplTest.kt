package com.example.themoviedatabase.repository.impl

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.themoviedatabase.cache.MovieCache
import com.example.themoviedatabase.database.dao.MovieDao
import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.mapper.domain.MovieDetailsMapper
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.network.MovieRequestQuery
import com.example.themoviedatabase.service.MovieService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    @Test
    fun get_movieSummaries_test() {
        // arrange
        val moviesFlow = flowOf(PagingData.from(listOf(MovieSummary())))
        val movieService: MovieService = mockk()

        coEvery { movieService.getMovieSummaries(any(), any()) } returns moviesFlow

        val repository = MovieRepositoryImpl(
            movieService = movieService,
            movieCache = mockk(),
            movieDao = mockk(),
            movieDetailsMapper = mockk()
        )

        // assert
        val pagingConfig = PagingConfig(pageSize = 10)
        val requestQuery = MovieRequestQuery.Builder()
        assertEquals(moviesFlow, repository.getMovieSummaries(pagingConfig, requestQuery))
    }

    @Test
    fun set_selected_movie_id_test() = runTest {
        // arrange
        val movieId = 1L
        val movieCache: MovieCache = mockk()

        coEvery { movieCache.setSelectedMovieId(any()) } returns Unit

        val repository = MovieRepositoryImpl(
            movieService = mockk(),
            movieCache = movieCache,
            movieDao = mockk(),
            movieDetailsMapper = mockk()
        )

        // act
        repository.setSelectedMovieId(movieId)

        // assert
        coVerify(exactly = 1) { movieCache.setSelectedMovieId(movieId) }
    }

    @Test
    fun get_selected_movie_id_test() = runTest {
        // arrange
        val movieId = 1L
        val movieCache: MovieCache = mockk()

        coEvery { movieCache.getSelectedMovieId() } returns flowOf(movieId)

        val repository = MovieRepositoryImpl(
            movieService = mockk(),
            movieCache = movieCache,
            movieDao = mockk(),
            movieDetailsMapper = mockk()
        )

        // assert
        val result = repository.getSelectedMovieId().first()
        assertEquals(movieId, result)
    }

    @Test
    fun get_movie_details_with_id_test() = runTest {
        // arrange
        val movieId = 1L
        val movieDetailsQuery = MovieDetailsQuery()
        val movieDetails = MovieDetails(movieId = movieId)
        val movieDao: MovieDao = mockk()
        val movieDetailsMapper: MovieDetailsMapper = mockk()

        coEvery { movieDao.getMovieDetailsWithId(any()) } returns flowOf(movieDetailsQuery)
        coEvery { movieDetailsMapper.map(any()) } returns movieDetails

        val repository = MovieRepositoryImpl(
            movieService = mockk(),
            movieCache = mockk(),
            movieDao = movieDao,
            movieDetailsMapper = movieDetailsMapper
        )

        // assert
        val result = repository.getMovieDetailsWithId(movieId).first()
        coVerify(exactly = 1) { movieDao.getMovieDetailsWithId(movieId) }
        coVerify(exactly = 1) { movieDetailsMapper.map(movieDetailsQuery) }
        assertEquals(movieDetails, result)
    }
}