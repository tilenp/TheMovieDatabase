package com.example.themoviedatabase.repository.impl

import com.example.themoviedatabase.database.dao.VideoDao
import com.example.themoviedatabase.database.table.VideoTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.model.dto.ResponseDTO
import com.example.themoviedatabase.model.dto.VideoDTO
import com.example.themoviedatabase.repository.VideoRepository
import com.example.themoviedatabase.service.MovieVideosService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepositoryImpl @Inject constructor(
    private val movieVideosService: MovieVideosService,
    private val videoDao: VideoDao,
    private val videoTableMapper: Mapper<ResponseDTO<VideoDTO>, List<@JvmSuppressWildcards VideoTable>>,
    private val videoMapper: Mapper<VideoTable, Video>
) : VideoRepository {

    override suspend fun updateVideosWithMovieId(movieId: Long) {
        delay(500)
        val responseVideos = movieVideosService.getMovieVideos(movieId)
        videoDao.insertVideos(videoTableMapper.map(responseVideos))
    }

    override fun getVideosWithMovieId(movieId: Long): Flow<List<Video>> {
        return videoDao.getVideosWithItemId(movieId)
            .map { movies -> movies.map { videoMapper.map(it) } }
    }
}