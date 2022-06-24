package com.example.themoviedatabase.use_case.impl

import com.example.themoviedatabase.model.Resource
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.use_case.UpdateSimilarMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateSimilarMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : UpdateSimilarMoviesUseCase {

    override fun invoke(movieId: Long, update: Boolean, page: Int?): Flow<Resource<List<MovieSummary>>> {
        return movieRepository.getSimilarMoviesForId(movieId)
            .map { Resource(data = it) }
            .onStart {
                if (update) {
                    try {
                        movieRepository.updateSimilarMoviesForId(movieId, page)
                    } catch (throwable: Throwable) {
                        emit(Resource(error = throwable))
                    }
                }
            }
    }
}