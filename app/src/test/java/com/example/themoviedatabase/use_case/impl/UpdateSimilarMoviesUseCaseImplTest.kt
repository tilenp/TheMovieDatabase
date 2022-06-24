package com.example.themoviedatabase.use_case.impl

import app.cash.turbine.test
import com.example.themoviedatabase.model.domain.MovieSummary
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
class UpdateSimilarMoviesUseCaseImplTest {

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
    fun get_similar_movies_test() = runTest {
        // arrange
        val movieId = 1L
        val page = 1
        val movieSummaries = listOf(MovieSummary(movieId = 1), MovieSummary(movieId = 2))
        val movieRepository: MovieRepository = mockk()

        coEvery { movieRepository.getSimilarMoviesForId(any()) } returns flowOf(movieSummaries)
        coEvery { movieRepository.updateSimilarMoviesForId(any(), any()) } returns Unit

        val useCase = UpdateSimilarMoviesUseCaseImpl(
            movieRepository = movieRepository
        )

        // assert
        useCase.invoke(movieId, false, page).test {
            assertEquals(movieSummaries, awaitItem().data)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 1) { movieRepository.getSimilarMoviesForId(movieId) }
        coVerify(exactly = 0) { movieRepository.updateSimilarMoviesForId(movieId, page) }
    }

    @Test
    fun update_similar_movies_test() = runTest {
        // arrange
        val movieId = 1L
        val page = 1
        val movieSummaries = listOf(MovieSummary(movieId = 1), MovieSummary(movieId = 2))
        val movieRepository: MovieRepository = mockk()

        coEvery { movieRepository.getSimilarMoviesForId(any()) } returns flowOf(movieSummaries)
        coEvery { movieRepository.updateSimilarMoviesForId(any(), any()) } returns Unit

        val useCase = UpdateSimilarMoviesUseCaseImpl(
            movieRepository = movieRepository
        )

        // assert
        useCase.invoke(movieId, true, page).test {
            assertEquals(movieSummaries, awaitItem().data)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 1) { movieRepository.getSimilarMoviesForId(movieId) }
        coVerify(exactly = 1) { movieRepository.updateSimilarMoviesForId(movieId, page) }
    }

    @Test
    fun update_similar_movies_error_test() = runTest {
        // arrange
        val movieId = 1L
        val page = 1
        val throwable = Throwable("throwable")
        val movieRepository: MovieRepository = mockk()

        coEvery { movieRepository.getSimilarMoviesForId(any()) } returns emptyFlow()
        coEvery { movieRepository.updateSimilarMoviesForId(any(), any()) } throws throwable

        val useCase = UpdateSimilarMoviesUseCaseImpl(
            movieRepository = movieRepository
        )

        // assert
        useCase.invoke(movieId, true, page).test {
            assertEquals(throwable, awaitItem().error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}