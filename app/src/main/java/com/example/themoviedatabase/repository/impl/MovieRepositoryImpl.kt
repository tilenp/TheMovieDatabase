package com.example.themoviedatabase.repository.impl

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.example.themoviedatabase.cache.MovieCache
import com.example.themoviedatabase.database.MovieDatabase
import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.database.table.GenreTable
import com.example.themoviedatabase.database.table.MovieGenreTable
import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.network.MovieRequestQuery
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.service.MovieDetailsService
import com.example.themoviedatabase.service.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDetailsService: MovieDetailsService,
    private val movieCache: MovieCache,
    private val database: MovieDatabase,
    private val movieTableMapper: Mapper<MovieDTO, MovieTable>,
    private val genreTableMapper: Mapper<MovieDTO, List<GenreTable>>,
    private val movieGenreTableMapper: Mapper<MovieDTO, List<MovieGenreTable>>,
    private val movieDetailsMapper: Mapper<MovieDetailsQuery, MovieDetails>
) : MovieRepository {

    private val movieDao = database.getMovieDao()
    private val genreDao = database.getGenreDao()
    private val movieGenreDao = database.getMovieGenreDao()

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

    override suspend fun updateMovieDetailsWithId(movieId: Long) {
        val movieDetailsDto = movieDetailsService.getMovieDetails(movieId)
        database.withTransaction {
            movieDao.updateMovie(movieTableMapper.map(movieDetailsDto))
            genreDao.upsert(genreTableMapper.map(movieDetailsDto))
            movieGenreDao.insertMovieGenres(movieGenreTableMapper.map(movieDetailsDto))
        }
    }

    override fun getMovieDetailsWithId(movieId: Long): Flow<MovieDetails> {
        return movieDao.getMovieDetailsWithId(movieId)
            .filterNotNull()
            .map { movieDetailsQuery -> movieDetailsMapper.map(movieDetailsQuery) }
    }
}