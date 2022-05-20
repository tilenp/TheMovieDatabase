package com.example.themoviedatabase.network

import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
import kotlinx.coroutines.delay
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMovieApi @Inject constructor(): MovieApi {

    private val queue = LinkedList<FakeResponse<PagingDTO<MovieDTO>>>()

    fun enqueue(fakeResponse: FakeResponse<PagingDTO<MovieDTO>>) {
        queue.push(fakeResponse)
    }

    override suspend fun getMovies(sortBy: String?, page: Int?): PagingDTO<MovieDTO> {
        return when {
            queue.isEmpty() -> throw IllegalStateException("response queue is empty")
            else -> handleResponse(queue.pollLast())
        }
    }

    private suspend fun handleResponse(response: FakeResponse<PagingDTO<MovieDTO>>): PagingDTO<MovieDTO> {
        return when {
            response.testLoading -> {
                delay(10000)
                PagingDTO()
            }
            response.code == 200 && response.body != null -> response.body
            else -> throw IOException("network not available")
        }
    }
}