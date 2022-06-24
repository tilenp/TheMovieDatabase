package com.example.themoviedatabase.ui.movie_details

import app.cash.turbine.test
import com.example.themoviedatabase.cache.MovieCache
import com.example.themoviedatabase.model.Resource
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.use_case.UpdateMovieDetailsUseCase
import com.example.themoviedatabase.use_case.UpdateSimilarMoviesUseCase
import com.example.themoviedatabase.use_case.UpdateVideosUseCase
import com.example.themoviedatabase.utils.FakeDispatcherProvider
import com.example.themoviedatabase.utils.UIText
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

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
        val movieDetails = MovieDetails(title = UIText("title"))
        val movieCache: MovieCache = mockk()
        val updateMovieDetailsUseCase: UpdateMovieDetailsUseCase = mockk()
        val updateVideosUseCase: UpdateVideosUseCase = mockk()
        val updateSimilarMoviesUseCase: UpdateSimilarMoviesUseCase = mockk()

        every { movieCache.getSelectedMovieId() } returns flowOf(movieId)
        every { updateMovieDetailsUseCase.invoke(any(), any()) } returns flowOf(Resource(data = movieDetails))
        every { updateVideosUseCase.invoke(any(), any()) } returns emptyFlow()
        every { updateSimilarMoviesUseCase.invoke(any(), any()) } returns emptyFlow()

        val viewModel = MovieDetailsViewModel(
            movieCache = movieCache,
            updateMovieDetailsUseCase = updateMovieDetailsUseCase,
            updateVideosUseCase = updateVideosUseCase,
            updateSimilarMoviesUseCase = updateSimilarMoviesUseCase,
            dispatcherProvider = FakeDispatcherProvider()
        )

        // assert
        viewModel.uiState.test {
            assertEquals(movieDetails, awaitItem().movieDetails)
        }
    }

    @Test
    fun load_movie_details_error_test() = runTest {
        // arrange
        val movieId = 1L
        val movieCache: MovieCache = mockk()
        val updateMovieDetailsUseCase: UpdateMovieDetailsUseCase = mockk()
        val updateVideosUseCase: UpdateVideosUseCase = mockk()
        val updateSimilarMoviesUseCase: UpdateSimilarMoviesUseCase = mockk()

        every { movieCache.getSelectedMovieId() } returns flowOf(movieId)
        every { updateMovieDetailsUseCase.invoke(any(), any()) } returns flowOf(Resource(error = Throwable()))
        every { updateVideosUseCase.invoke(any(), any()) } returns emptyFlow()
        every { updateSimilarMoviesUseCase.invoke(any(), any()) } returns emptyFlow()

        val viewModel = MovieDetailsViewModel(
            movieCache = movieCache,
            updateMovieDetailsUseCase = updateMovieDetailsUseCase,
            updateVideosUseCase = updateVideosUseCase,
            updateSimilarMoviesUseCase = updateSimilarMoviesUseCase,
            dispatcherProvider = FakeDispatcherProvider()
        )

        // assert
        viewModel.uiState.test {
            assertNotNull(awaitItem().error)
        }
    }

    @Test
    fun load_videos_test() = runTest {
        // arrange
        val movieId = 1L
        val videos = listOf(Video(id = "videoId"))
        val movieCache: MovieCache = mockk()
        val updateMovieDetailsUseCase: UpdateMovieDetailsUseCase = mockk()
        val updateVideosUseCase: UpdateVideosUseCase = mockk()
        val updateSimilarMoviesUseCase: UpdateSimilarMoviesUseCase = mockk()

        every { movieCache.getSelectedMovieId() } returns flowOf(movieId)
        every { updateMovieDetailsUseCase.invoke(any(), any()) } returns emptyFlow()
        every { updateVideosUseCase.invoke(any(), any()) } returns flowOf(Resource(data = videos))
        every { updateSimilarMoviesUseCase.invoke(any(), any()) } returns emptyFlow()

        val viewModel = MovieDetailsViewModel(
            movieCache = movieCache,
            updateMovieDetailsUseCase = updateMovieDetailsUseCase,
            updateVideosUseCase = updateVideosUseCase,
            updateSimilarMoviesUseCase = updateSimilarMoviesUseCase,
            dispatcherProvider = FakeDispatcherProvider()
        )

        // assert
        viewModel.uiState.test {
            assertEquals(videos, awaitItem().videos)
        }
    }

    @Test
    fun load_videos_error_test() = runTest {
        // arrange
        val movieId = 1L
        val movieCache: MovieCache = mockk()
        val updateMovieDetailsUseCase: UpdateMovieDetailsUseCase = mockk()
        val updateVideosUseCase: UpdateVideosUseCase = mockk()
        val updateSimilarMoviesUseCase: UpdateSimilarMoviesUseCase = mockk()

        every { movieCache.getSelectedMovieId() } returns flowOf(movieId)
        every { updateMovieDetailsUseCase.invoke(any(), any()) } returns emptyFlow()
        every { updateVideosUseCase.invoke(any(), any()) } returns flowOf(Resource(error = Throwable()))
        every { updateSimilarMoviesUseCase.invoke(any(), any()) } returns emptyFlow()

        val viewModel = MovieDetailsViewModel(
            movieCache = movieCache,
            updateMovieDetailsUseCase = updateMovieDetailsUseCase,
            updateVideosUseCase = updateVideosUseCase,
            updateSimilarMoviesUseCase = updateSimilarMoviesUseCase,
            dispatcherProvider = FakeDispatcherProvider()
        )

        // assert
        viewModel.uiState.test {
            assertNotNull(awaitItem().error)
        }
    }

    @Test
    fun load_similar_movies_test() = runTest {
        // arrange
        val movieId = 1L
        val similarMovies = listOf(MovieSummary(title = UIText("title")))
        val movieCache: MovieCache = mockk()
        val updateMovieDetailsUseCase: UpdateMovieDetailsUseCase = mockk()
        val updateVideosUseCase: UpdateVideosUseCase = mockk()
        val updateSimilarMoviesUseCase: UpdateSimilarMoviesUseCase = mockk()

        every { movieCache.getSelectedMovieId() } returns flowOf(movieId)
        every { updateMovieDetailsUseCase.invoke(any(), any()) } returns emptyFlow()
        every { updateVideosUseCase.invoke(any(), any()) } returns emptyFlow()
        every { updateSimilarMoviesUseCase.invoke(any(), any()) } returns flowOf(Resource(data = similarMovies))

        val viewModel = MovieDetailsViewModel(
            movieCache = movieCache,
            updateMovieDetailsUseCase = updateMovieDetailsUseCase,
            updateVideosUseCase = updateVideosUseCase,
            updateSimilarMoviesUseCase = updateSimilarMoviesUseCase,
            dispatcherProvider = FakeDispatcherProvider()
        )

        // assert
        viewModel.uiState.test {
            assertEquals(similarMovies, awaitItem().similarMovies)
        }
    }

    @Test
    fun load_similar_movies_error_test() = runTest {
        // arrange
        val movieId = 1L
        val movieCache: MovieCache = mockk()
        val updateMovieDetailsUseCase: UpdateMovieDetailsUseCase = mockk()
        val updateVideosUseCase: UpdateVideosUseCase = mockk()
        val updateSimilarMoviesUseCase: UpdateSimilarMoviesUseCase = mockk()

        every { movieCache.getSelectedMovieId() } returns flowOf(movieId)
        every { updateMovieDetailsUseCase.invoke(any(), any()) } returns emptyFlow()
        every { updateVideosUseCase.invoke(any(), any()) } returns emptyFlow()
        every { updateSimilarMoviesUseCase.invoke(any(), any()) } returns flowOf(Resource(error = Throwable()))

        val viewModel = MovieDetailsViewModel(
            movieCache = movieCache,
            updateMovieDetailsUseCase = updateMovieDetailsUseCase,
            updateVideosUseCase = updateVideosUseCase,
            updateSimilarMoviesUseCase = updateSimilarMoviesUseCase,
            dispatcherProvider = FakeDispatcherProvider()
        )

        // assert
        viewModel.uiState.test {
            assertNotNull(awaitItem().error)
        }
    }

    @Test
    fun load_movie_details_and_videos_test() = runTest {
        // arrange
        val movieId = 1L
        val movieDetails = MovieDetails(title = UIText("title"))
        val videos = listOf(Video(id = "videoId"))
        val movieCache: MovieCache = mockk()
        val updateMovieDetailsUseCase: UpdateMovieDetailsUseCase = mockk()
        val updateVideosUseCase: UpdateVideosUseCase = mockk()
        val updateSimilarMoviesUseCase: UpdateSimilarMoviesUseCase = mockk()

        every { movieCache.getSelectedMovieId() } returns flowOf(movieId)
        every { updateMovieDetailsUseCase.invoke(any(), any()) } returns flowOf(Resource(data = movieDetails))
        every { updateVideosUseCase.invoke(any(), any()) } returns flowOf(Resource(data = videos))
        every { updateSimilarMoviesUseCase.invoke(any(), any()) } returns emptyFlow()

        val viewModel = MovieDetailsViewModel(
            movieCache = movieCache,
            updateMovieDetailsUseCase = updateMovieDetailsUseCase,
            updateVideosUseCase = updateVideosUseCase,
            updateSimilarMoviesUseCase = updateSimilarMoviesUseCase,
            dispatcherProvider = FakeDispatcherProvider()
        )

        // assert
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(movieDetails, state.movieDetails)
            assertEquals(videos, state.videos)
        }
    }

    @Test
    fun new_action_test() = runTest {
        // arrange
        val movieId = 1L
        val movieCache: MovieCache = mockk()
        val updateMovieDetailsUseCase: UpdateMovieDetailsUseCase = mockk()
        val updateVideosUseCase: UpdateVideosUseCase = mockk()
        val updateSimilarMoviesUseCase: UpdateSimilarMoviesUseCase = mockk()

        every { movieCache.getSelectedMovieId() } returns flowOf(movieId)
        every { updateMovieDetailsUseCase.invoke(any(), any()) } returns emptyFlow()
        every { updateVideosUseCase.invoke(any(), any()) } returns emptyFlow()
        every { updateSimilarMoviesUseCase.invoke(any(), any()) } returns emptyFlow()

        val viewModel = MovieDetailsViewModel(
            movieCache = movieCache,
            updateMovieDetailsUseCase = updateMovieDetailsUseCase,
            updateVideosUseCase = updateVideosUseCase,
            updateSimilarMoviesUseCase = updateSimilarMoviesUseCase,
            dispatcherProvider = FakeDispatcherProvider()
        )

        val event = Event.Load.Builder()
            .add(ActionType.LOAD_MOVIE_DETAILS)
            .add(ActionType.LOAD_VIDEOS)
            .build()
        viewModel.newEvent(event)

        // assert
        verify(exactly = 2) { updateMovieDetailsUseCase.invoke(movieId, true) }
        verify(exactly = 2) { updateVideosUseCase.invoke(movieId, true) }
        verify(exactly = 1) { updateSimilarMoviesUseCase.invoke(movieId, true) }
        verify(exactly = 1) { updateSimilarMoviesUseCase.invoke(movieId, false) }
    }
}