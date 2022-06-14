package com.example.themoviedatabase.use_case.impl

import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.repository.VideoRepository
import com.example.themoviedatabase.use_case.UpdateVideosUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateVideosUseCaseImpl @Inject constructor(
    private val videoRepository: VideoRepository
) : UpdateVideosUseCase {

    override suspend fun invoke(movieId: Long): Flow<List<Video>> {
        return flow { emit(videoRepository.updateVideosWithMovieId(movieId)) }
            .flatMapConcat { videoRepository.getVideosWithMovieId(movieId) }
    }
}