package com.example.themoviedatabase.use_case.impl

import com.example.themoviedatabase.model.Resource
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.use_case.UpdateMovieDetailsUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateMovieDetailsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : UpdateMovieDetailsUseCase {

    override suspend fun invoke(movieId: Long): Flow<Resource<MovieDetails>> {
        return movieRepository.getMovieDetailsWithId(movieId)
            .map { Resource(data = it) }
            .onStart {
                try {
                    movieRepository.updateMovieDetailsWithId(movieId)
                } catch (throwable: Throwable) {
                    emit(Resource(error = throwable))
                }
            }
    }
}