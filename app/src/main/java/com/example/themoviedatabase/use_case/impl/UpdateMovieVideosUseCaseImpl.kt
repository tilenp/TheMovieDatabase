package com.example.themoviedatabase.use_case.impl

import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.use_case.UpdateMovieVideosUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateMovieVideosUseCaseImpl @Inject constructor(
    val movieRepository: MovieRepository
) : UpdateMovieVideosUseCase {

    override suspend fun invoke(movieId: Long): Flow<List<Video>> {
        return flow { emit(movieRepository.updateVideosWithMovieId(movieId)) }
            .flatMapConcat { movieRepository.getVideosWithMovieId(movieId) }
    }
}