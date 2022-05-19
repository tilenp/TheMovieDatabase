package com.example.themoviedatabase.service.impl

import androidx.paging.*
import com.example.themoviedatabase.database.MovieDatabase
import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.network.MovieApi
import com.example.themoviedatabase.network.MovieRequestQuery
import com.example.themoviedatabase.service.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class MovieServiceImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val database: MovieDatabase,
    private val movieTableMapper: Mapper<MovieDTO, MovieTable>,
    private val imagePathTableMapper: Mapper<MovieDTO, ImagePathTable>,
    private val movieSummaryMapper: Mapper<MovieSummaryQuery, MovieSummary>
) : MovieService {

    override fun getMovieSummaries(
        pagingConfig: PagingConfig,
        requestQuery: MovieRequestQuery.Builder
    ): Flow<PagingData<MovieSummary>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = MovieRemoteMediator(
                movieApi = movieApi,
                database = database,
                queryBuilder = requestQuery,
                movieTableMapper = movieTableMapper,
                imagePathTableMapper = imagePathTableMapper,
            ),
            pagingSourceFactory = {
                database.getMovieDao().getPopularMovies()
            }
        ).flow
            .map { pagingData ->
                pagingData.map { queryCharacter ->
                    movieSummaryMapper.map(queryCharacter)
                }
            }
    }
}