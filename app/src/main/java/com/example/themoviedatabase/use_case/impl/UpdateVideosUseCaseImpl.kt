package com.example.themoviedatabase.use_case.impl

import com.example.themoviedatabase.model.Resource
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.repository.VideoRepository
import com.example.themoviedatabase.use_case.UpdateVideosUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateVideosUseCaseImpl @Inject constructor(
    private val videoRepository: VideoRepository
) : UpdateVideosUseCase {

    override fun invoke(movieId: Long): Flow<Resource<List<Video>>> {
        return videoRepository.getVideosWithMovieId(movieId)
            .map { Resource(data = it) }
            .onStart {
                try {
                    videoRepository.updateVideosWithMovieId(movieId)
                } catch (throwable: Throwable) {
                    emit(Resource(error = throwable))
                }
            }
    }
}