package com.example.themoviedatabase.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.network.MovieRequestQuery
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovieSummaries(
        pagingConfig: PagingConfig,
        requestQuery: MovieRequestQuery.Builder
    ): Flow<PagingData<MovieSummary>>

    suspend fun setSelectedMovieId(movieId: Long)

    fun getSelectedMovieId(): Flow<Long>

    suspend fun updateMovieDetailsWithId(movieId: Long)

    fun getMovieDetailsWithId(movieId: Long): Flow<MovieDetails>

    suspend fun updateVideosWithMovieId(movieId: Long)

    fun getVideosWithMovieId(movieId: Long): Flow<List<Video>>
}