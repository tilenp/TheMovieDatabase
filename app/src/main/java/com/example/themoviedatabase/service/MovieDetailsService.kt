package com.example.themoviedatabase.service

import com.example.themoviedatabase.model.dto.MovieDTO

interface MovieDetailsService {
    suspend fun getMovieDetails(
        movieId: Long
    ): MovieDTO
}