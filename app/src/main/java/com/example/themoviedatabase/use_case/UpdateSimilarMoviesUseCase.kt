package com.example.themoviedatabase.use_case

import com.example.themoviedatabase.model.Resource
import com.example.themoviedatabase.model.domain.MovieSummary
import kotlinx.coroutines.flow.Flow

interface UpdateSimilarMoviesUseCase {
    fun invoke(movieId: Long, page: Int? = null): Flow<Resource<List<MovieSummary>>>
}