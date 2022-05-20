package com.example.themoviedatabase.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.network.MovieRequestQuery
import com.example.themoviedatabase.repository.impl.MovieRepositoryImpl
import com.example.themoviedatabase.service.MovieService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieRepositoryImplTest {

    @Test
    fun get_movieSummaries_test() {
        // arrange
        val moviesFlow = flowOf(PagingData.from(listOf(MovieSummary())))
        val movieService: MovieService = mockk()

        coEvery { movieService.getMovieSummaries(any(), any()) } returns moviesFlow

        val repository = MovieRepositoryImpl(
            movieService = movieService
        )

        // assert
        val pagingConfig = PagingConfig(pageSize = 10)
        val requestQuery = MovieRequestQuery.Builder()
        assertEquals(moviesFlow, repository.getMovieSummaries(pagingConfig, requestQuery))
    }
}