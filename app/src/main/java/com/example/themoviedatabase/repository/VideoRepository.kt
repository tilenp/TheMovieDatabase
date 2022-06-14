package com.example.themoviedatabase.repository

import com.example.themoviedatabase.model.domain.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun updateVideosWithMovieId(movieId: Long)

    fun getVideosWithMovieId(movieId: Long): Flow<List<Video>>
}