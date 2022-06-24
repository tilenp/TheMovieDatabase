package com.example.themoviedatabase.use_case.impl

import app.cash.turbine.test
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.utils.FakeDispatcherProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateMovieDetailsUseCaseImplTest {

    private val dispatcher = FakeDispatcherProvider()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher.testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun get_movie_details_test() = runTest {
        // arrange
        val movieId = 1L
        val movieDetails = MovieDetails()
        val movieRepository: MovieRepository = mockk()

        coEvery { movieRepository.getMovieDetailsWithId(any()) } returns flowOf(movieDetails)
        coEvery { movieRepository.updateMovieDetailsWithId(any()) } returns Unit

        val useCase = UpdateMovieDetailsUseCaseImpl(
            movieRepository = movieRepository
        )

        // assert
        useCase.invoke(movieId, false).test {
            assertEquals(movieDetails, awaitItem().data)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 1) { movieRepository.getMovieDetailsWithId(movieId) }
        coVerify(exactly = 0) { movieRepository.updateMovieDetailsWithId(movieId) }
    }

    @Test
    fun update_movie_details_test() = runTest {
        // arrange
        val movieId = 1L
        val movieDetails = MovieDetails()
        val movieRepository: MovieRepository = mockk()

        coEvery { movieRepository.getMovieDetailsWithId(any()) } returns flowOf(movieDetails)
        coEvery { movieRepository.updateMovieDetailsWithId(any()) } returns Unit

        val useCase = UpdateMovieDetailsUseCaseImpl(
            movieRepository = movieRepository
        )

        // assert
        useCase.invoke(movieId, true).test {
            assertEquals(movieDetails, awaitItem().data)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 1) { movieRepository.getMovieDetailsWithId(movieId) }
        coVerify(exactly = 1) { movieRepository.updateMovieDetailsWithId(movieId) }
    }

    @Test
    fun update_movie_details_error_test() = runTest {
        // arrange
        val movieId = 1L
        val throwable = Throwable("throwable")
        val movieRepository: MovieRepository = mockk()

        coEvery { movieRepository.getMovieDetailsWithId(any()) } returns emptyFlow()
        coEvery { movieRepository.updateMovieDetailsWithId(any()) } throws throwable

        val useCase = UpdateMovieDetailsUseCaseImpl(
            movieRepository = movieRepository
        )

        // assert
        useCase.invoke(movieId, true).test {
            assertEquals(throwable, awaitItem().error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}