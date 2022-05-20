package com.example.themoviedatabase.ui.movie_screen

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.ui.movies_screen.MoviesViewModel
import com.example.themoviedatabase.utils.*
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
class MoviesViewModelTest {

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
    fun movies_test() = runTest {
        // arrange
        val movies = listOf(MovieSummary(movieId = 1), MovieSummary(movieId = 2))
        val movieRepository: MovieRepository = mockk()
        val errorMessageHandler: ErrorMessageHandler = mockk()

        coEvery { movieRepository.getMovieSummaries(any(), any()) } returns flowOf(PagingData.from(movies))
        coEvery { movieRepository.setSelectedMovieId(any()) } returns Unit

        val viewModel = MoviesViewModel(
            movieRepository = movieRepository,
            errorMessageHandler = errorMessageHandler,
            dispatcherProvider = dispatcher
        )

        val differ = AsyncPagingDataDiffer(
            diffCallback = FakeDiffUtil<MovieSummary>(),
            updateCallback = FakeListUpdateCallback(),
            workerDispatcher = Dispatchers.Main
        )

        // assert
        viewModel.movies.test {
            differ.submitData(awaitItem())
            assertEquals(movies, differ.snapshot().items)
        }
    }

    @Test
    fun get_error_message_test() = runTest {
        // arrange
        val throwable = Throwable()
        val messageId = R.string.Unknown_error
        val movieRepository: MovieRepository = mockk()
        val errorMessageHandler: ErrorMessageHandler = mockk()

        coEvery { movieRepository.getMovieSummaries(any(), any()) } returns emptyFlow()
        coEvery { errorMessageHandler.getExceptionMessage(any()) } returns messageId

        val viewModel = MoviesViewModel(
            movieRepository = movieRepository,
            errorMessageHandler = errorMessageHandler,
            dispatcherProvider = dispatcher
        )

        // assert
        val result = viewModel.getErrorMessage(throwable)
        assertEquals(messageId, result)
        coVerify(exactly = 1) { errorMessageHandler.getExceptionMessage(throwable) }
    }

    @Test
    fun action_select_movie_is_throttled() = runTest {
        // arrange
        val movieId = 1L
        val movieRepository: MovieRepository = mockk()
        val errorMessageHandler: ErrorMessageHandler = mockk()

        coEvery { movieRepository.getMovieSummaries(any(), any()) } returns emptyFlow()
        coEvery { movieRepository.setSelectedMovieId(any()) } returns Unit

        val viewModel = MoviesViewModel(
            movieRepository = movieRepository,
            errorMessageHandler = errorMessageHandler,
            dispatcherProvider = dispatcher
        )

        // act 1
        viewModel.newAction(MoviesViewModel.Action.SelectMovie(movieId))
        viewModel.newAction(MoviesViewModel.Action.SelectMovie(movieId))

        // act 2
        dispatcher.scope.testScheduler.advanceTimeBy(THROTTLE_INTERVAL + 1)
        viewModel.newAction(MoviesViewModel.Action.SelectMovie(movieId))

        // assert
        coVerify(exactly = 2) { movieRepository.setSelectedMovieId(movieId) }
    }
}