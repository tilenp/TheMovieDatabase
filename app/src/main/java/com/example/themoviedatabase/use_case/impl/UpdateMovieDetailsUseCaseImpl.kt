package com.example.themoviedatabase.use_case.impl

import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.use_case.UpdateMovieDetailsUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateMovieDetailsUseCaseImpl @Inject constructor(
    val movieRepository: MovieRepository
) : UpdateMovieDetailsUseCase {

    override suspend fun invoke(movieId: Long): Flow<MovieDetails> {
        return flow { emit(movieRepository.updateMovieDetailsWithId(movieId)) }
            .flatMapConcat { movieRepository.getMovieDetailsWithId(movieId) }
    }
}