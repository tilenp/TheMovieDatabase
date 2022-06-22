package com.example.themoviedatabase.use_case.impl

import app.cash.turbine.test
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.repository.VideoRepository
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
class UpdateVideosUseCaseImplTest {

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
    fun update_videos_test() = runTest {
        // arrange
        val movieId = 1L
        val videos = listOf(Video(id = "id1"), Video(id = "id2"))
        val videoRepository: VideoRepository = mockk()

        coEvery { videoRepository.getVideosWithMovieId(any()) } returns flowOf(videos)
        coEvery { videoRepository.updateVideosWithMovieId(any()) } returns Unit

        val service = UpdateVideosUseCaseImpl(
            videoRepository = videoRepository
        )

        // assert
        service.invoke(movieId).test {
            assertEquals(videos, awaitItem().data)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 1) { videoRepository.getVideosWithMovieId(movieId) }
        coVerify(exactly = 1) { videoRepository.updateVideosWithMovieId(movieId) }
    }

    @Test
    fun update_similar_movies_error_test() = runTest {
        // arrange
        val movieId = 1L
        val throwable = Throwable("throwable")
        val videoRepository: VideoRepository = mockk()

        coEvery { videoRepository.getVideosWithMovieId(any()) } returns emptyFlow()
        coEvery { videoRepository.updateVideosWithMovieId(any()) } throws throwable

        val service = UpdateVideosUseCaseImpl(
            videoRepository = videoRepository
        )

        // assert
        service.invoke(movieId).test {
            assertEquals(throwable, awaitItem().error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}