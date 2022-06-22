package com.example.themoviedatabase.service.impl

import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
import com.example.themoviedatabase.network.MovieApi
import com.example.themoviedatabase.utils.FakeDispatcherProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SimilarMoviesServiceImplTest {

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
    fun load_similar_movies_test() = runTest {
        // arrange
        val movieId = 1L
        val page = 1
        val movieDTOs = listOf(MovieDTO(id = 1L), MovieDTO(id = 2L))
        val response = PagingDTO(page = page, totalPages = 1, results = movieDTOs)
        val movieApi: MovieApi = mockk()

        coEvery { movieApi.getSimilarMovies(any(), any()) } returns response

        val service = SimilarMoviesServiceImpl(
            movieApi = movieApi
        )

        // act
        val result = service.getSimilarMovies(movieId, page)

        // assert
        assertEquals(result, response)
        coVerify(exactly = 1) { movieApi.getSimilarMovies(movieId, page) }
    }
}