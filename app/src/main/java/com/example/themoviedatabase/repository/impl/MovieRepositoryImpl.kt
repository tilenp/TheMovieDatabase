package com.example.themoviedatabase.repository.impl

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.themoviedatabase.cache.MovieCache
import com.example.themoviedatabase.database.dao.MovieDao
import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.network.MovieRequestQuery
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.service.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieCache: MovieCache,
    private val movieDao: MovieDao,
    private val movieDetailsMapper: Mapper<MovieDetailsQuery, MovieDetails>
) : MovieRepository {

    override fun getMovieSummaries(
        pagingConfig: PagingConfig,
        requestQuery: MovieRequestQuery.Builder
    ): Flow<PagingData<MovieSummary>> {
        return movieService.getMovieSummaries(
            pagingConfig = pagingConfig,
            requestQuery = requestQuery
        )
    }

    override suspend fun setSelectedMovieId(movieId: Long) {
        movieCache.setSelectedMovieId(movieId)
    }

    override fun getSelectedMovieId(): Flow<Long> {
        return movieCache.getSelectedMovieId()
    }

    override fun getMovieDetailsWithId(movieId: Long): Flow<MovieDetails> {
        return movieDao.getMovieDetailsWithId(movieId)
            .filterNotNull()
            .map { movieDetailsQuery -> movieDetailsMapper.map(movieDetailsQuery) }
    }
}