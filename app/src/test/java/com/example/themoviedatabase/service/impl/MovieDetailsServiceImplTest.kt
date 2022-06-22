package com.example.themoviedatabase.service.impl

import com.example.themoviedatabase.model.dto.MovieDTO
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
class MovieDetailsServiceImplTest {

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
    fun load_movie_details_test() = runTest {
        // arrange
        val movieId = 1L
        val movieDTO = MovieDTO(id = movieId)
        val movieApi: MovieApi = mockk()

        coEvery { movieApi.getMovieDetails(any()) } returns movieDTO

        val service = MovieDetailsServiceImpl(
            movieApi = movieApi
        )

        // act
        val result = service.getMovieDetails(movieId)

        // assert
        assertEquals(result, movieDTO)
        coVerify(exactly = 1) { movieApi.getMovieDetails(movieId) }
    }
}