package com.example.themoviedatabase.service.impl

import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
import com.example.themoviedatabase.network.MovieApi
import com.example.themoviedatabase.service.SimilarMoviesService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimilarMoviesServiceImpl @Inject constructor(
    private val movieApi: MovieApi
) : SimilarMoviesService {

    override suspend fun getSimilarMovies(movieId: Long, page: Int?): PagingDTO<MovieDTO> {
        return movieApi.getSimilarMovies(movieId, page)
    }
}