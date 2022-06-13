package com.example.themoviedatabase.service.impl

import com.example.themoviedatabase.model.dto.ResponseDTO
import com.example.themoviedatabase.model.dto.VideoDTO
import com.example.themoviedatabase.network.MovieApi
import com.example.themoviedatabase.service.MovieVideosService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieVideosServiceImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieVideosService {

    override suspend fun getMovieVideos(movieId: Long): ResponseDTO<VideoDTO> {
        return movieApi.getMovieVideos(movieId)
    }
}