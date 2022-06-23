package com.example.themoviedatabase.network

import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
import com.example.themoviedatabase.model.dto.ResponseDTO
import com.example.themoviedatabase.model.dto.VideoDTO
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMovieApi @Inject constructor(): MovieApi {

    private val movieSummariesQueue = LinkedList<FakeResponse<PagingDTO<MovieDTO>>>()
    private val movieDetailsQueue = LinkedList<FakeResponse<MovieDTO>>()
    private val videosQueue = LinkedList<FakeResponse<ResponseDTO<VideoDTO>>>()

    fun enqueueMovieSummaries(fakeResponse: FakeResponse<PagingDTO<MovieDTO>>) {
        movieSummariesQueue.push(fakeResponse)
    }

    fun enqueueMovieDetails(fakeResponse: FakeResponse<MovieDTO>) {
        movieDetailsQueue.push(fakeResponse)
    }

    fun enqueueVideos(fakeResponse: FakeResponse<ResponseDTO<VideoDTO>>) {
        videosQueue.push(fakeResponse)
    }

    override suspend fun getMovies(sortBy: String?, page: Int?): PagingDTO<MovieDTO> {
        return when {
            movieSummariesQueue.isEmpty() -> throw IllegalStateException("response queue is empty")
            else -> handleResponse(movieSummariesQueue.pollLast())
        }
    }

    override suspend fun getMovieDetails(movieId: Long): MovieDTO {
        return when {
            movieDetailsQueue.isEmpty() -> throw IllegalStateException("response queue is empty")
            else -> handleResponse(movieDetailsQueue.pollLast())
        }
    }

    override suspend fun getMovieVideos(movieId: Long): ResponseDTO<VideoDTO> {
        return when {
            videosQueue.isEmpty() -> throw IllegalStateException("response queue is empty")
            else -> handleResponse(videosQueue.pollLast())
        }
    }

    override suspend fun getSimilarMovies(movieId: Long, page: Int?): PagingDTO<MovieDTO> {
        return when {
            movieSummariesQueue.isEmpty() -> throw IllegalStateException("response queue is empty")
            else -> handleResponse(movieSummariesQueue.pollLast())
        }
    }

    private suspend fun <T: Any> handleResponse(response: FakeResponse<T>): T {
        return when {
            response.testLoading -> {
                delay(10000)
                response.body ?: throw IllegalStateException("response body is null")
            }
            response.code == 200 && response.body != null -> response.body
            response.throwable != null -> throw response.throwable
            else -> throw IllegalStateException("response not configured")
        }
    }
}