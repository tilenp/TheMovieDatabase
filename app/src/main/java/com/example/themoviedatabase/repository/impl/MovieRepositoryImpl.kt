package com.example.themoviedatabase.repository.impl

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.network.MovieRequestQuery
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.service.MovieService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
): MovieRepository {

    override fun getMovieSummaries(
        pagingConfig: PagingConfig,
        requestQuery: MovieRequestQuery.Builder
    ): Flow<PagingData<MovieSummary>> {
        return movieService.getMovieSummaries(
            pagingConfig = pagingConfig,
            requestQuery = requestQuery
        )
    }

    override suspend fun setSelectedMovieId(characterId: Long) {

    }
}