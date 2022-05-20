package com.example.themoviedatabase.ui.movie_screen

import com.example.themoviedatabase.R
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.ui.movies_screen.MoviesViewModel
import com.example.themoviedatabase.utils.ErrorMessageHandler
import com.example.themoviedatabase.utils.FakeDispatcherProvider
import com.example.themoviedatabase.utils.THROTTLE_INTERVAL
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    @Test
    fun get_error_message_test() = runTest {
        // arrange
        val throwable = Throwable()
        val messageId = R.string.Unknown_error
        val movieRepository: MovieRepository = mockk()
        val errorMessageHandler: ErrorMessageHandler = mockk()
        val dispatcher = FakeDispatcherProvider()

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
        val dispatcher = FakeDispatcherProvider()

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