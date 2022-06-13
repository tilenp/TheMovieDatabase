package com.example.themoviedatabase.service

import com.example.themoviedatabase.model.dto.ResponseDTO
import com.example.themoviedatabase.model.dto.VideoDTO

interface MovieVideosService {
    suspend fun getMovieVideos(
        movieId: Long
    ): ResponseDTO<VideoDTO>
}