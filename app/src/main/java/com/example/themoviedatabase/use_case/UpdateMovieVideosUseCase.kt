package com.example.themoviedatabase.use_case

import com.example.themoviedatabase.model.domain.Video
import kotlinx.coroutines.flow.Flow

interface UpdateMovieVideosUseCase {
    suspend fun invoke(movieId: Long): Flow<List<Video>>
}