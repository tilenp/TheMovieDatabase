package com.example.themoviedatabase.use_case

import com.example.themoviedatabase.model.Resource
import com.example.themoviedatabase.model.domain.MovieDetails
import kotlinx.coroutines.flow.Flow

interface UpdateMovieDetailsUseCase {
    fun invoke(movieId: Long): Flow<Resource<MovieDetails>>
}