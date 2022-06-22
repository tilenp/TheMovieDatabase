package com.example.themoviedatabase.service.impl

import com.example.themoviedatabase.model.dto.ResponseDTO
import com.example.themoviedatabase.model.dto.VideoDTO
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
class MovieVideosServiceImplTest {

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
    fun load_movie_videos_test() = runTest {
        // arrange
        val movieId = 1L
        val videoDTOs = listOf(VideoDTO(id = "id1"), VideoDTO(id = "id2"))
        val response = ResponseDTO(results = videoDTOs)
        val movieApi: MovieApi = mockk()

        coEvery { movieApi.getMovieVideos(any()) } returns response

        val service = MovieVideosServiceImpl(
            movieApi = movieApi
        )

        // act
        val result = service.getMovieVideos(movieId)

        // assert
        assertEquals(result, response)
        coVerify(exactly = 1) { movieApi.getMovieVideos(movieId) }
    }
}