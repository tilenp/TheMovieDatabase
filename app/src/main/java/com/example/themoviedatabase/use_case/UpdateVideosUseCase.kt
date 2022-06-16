package com.example.themoviedatabase.use_case

import com.example.themoviedatabase.model.Resource
import com.example.themoviedatabase.model.domain.Video
import kotlinx.coroutines.flow.Flow

interface UpdateVideosUseCase {
    suspend fun invoke(movieId: Long): Flow<Resource<List<Video>>>
}