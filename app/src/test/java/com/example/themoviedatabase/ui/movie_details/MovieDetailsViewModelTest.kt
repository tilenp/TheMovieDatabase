package com.example.themoviedatabase.ui.movie_details

import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.utils.FakeDispatcherProvider
import com.example.themoviedatabase.utils.UIText
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
class MovieDetailsViewModelTest {

    @Test
    fun content_test() = runTest {
        // arrange
        val movieId = 1L
        val movieDetails = MovieDetails(title = UIText("title"))
        val movieRepository: MovieRepository = mockk()

        coEvery { movieRepository.getSelectedMovieId() } returns flowOf(movieId)
        coEvery { movieRepository.getMovieDetailsWithId(any()) } returns flowOf(movieDetails)

        val viewModel = MovieDetailsViewModel(
            movieRepository = movieRepository,
            dispatcherProvider = FakeDispatcherProvider()
        )

        // assert
        val result = viewModel.uiState.first()
        assertEquals(movieDetails, result.movieDetails)
        coVerify(exactly = 1) { movieRepository.getMovieDetailsWithId(movieId) }
    }
}