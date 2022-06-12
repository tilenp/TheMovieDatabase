package com.example.themoviedatabase.use_case

import com.example.themoviedatabase.model.domain.MovieDetails
import kotlinx.coroutines.flow.Flow

interface UpdateMovieDetailsUseCase {
    suspend fun invoke(movieId: Long): Flow<MovieDetails>
}