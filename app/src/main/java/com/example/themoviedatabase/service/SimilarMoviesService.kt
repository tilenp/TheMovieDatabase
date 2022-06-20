package com.example.themoviedatabase.service

import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO

interface SimilarMoviesService {
    suspend fun getSimilarMovies(
        movieId: Long,
        page: Int?
    ): PagingDTO<MovieDTO>
}