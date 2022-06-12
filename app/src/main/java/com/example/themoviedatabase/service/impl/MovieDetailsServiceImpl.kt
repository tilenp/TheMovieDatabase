package com.example.themoviedatabase.service.impl

import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.network.MovieApi
import com.example.themoviedatabase.service.MovieDetailsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsServiceImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieDetailsService {

    override suspend fun getMovieDetails(movieId: Long): MovieDTO {
        return movieApi.getMovieDetails(movieId)
    }
}