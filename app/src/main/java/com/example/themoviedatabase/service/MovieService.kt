package com.example.themoviedatabase.service

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.network.MovieRequestQuery
import kotlinx.coroutines.flow.Flow

interface MovieService {
    fun getMovieSummaries(
        pagingConfig: PagingConfig,
        requestQuery: MovieRequestQuery.Builder
    ): Flow<PagingData<MovieSummary>>
}