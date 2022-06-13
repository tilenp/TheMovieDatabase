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
import com.example.themoviedatabase.database.table.VideoTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.ResponseDTO
import com.example.themoviedatabase.model.dto.VideoDTO
import com.example.themoviedatabase.network.MovieRequestQuery
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.service.MovieDetailsService
import com.example.themoviedatabase.service.MovieService
import com.example.themoviedatabase.service.MovieVideosService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDetailsService: MovieDetailsService,
    private val movieVideosService: MovieVideosService,
    private val movieCache: MovieCache,
    private val database: MovieDatabase,
    private val movieTableMapper: Mapper<MovieDTO, MovieTable>,
    private val genreTableMapper: Mapper<MovieDTO, List<GenreTable>>,
    private val movieGenreTableMapper: Mapper<MovieDTO, List<MovieGenreTable>>,
    private val videoTableMapper: Mapper<ResponseDTO<VideoDTO>, List<@JvmSuppressWildcards VideoTable>>,
    private val movieDetailsMapper: Mapper<MovieDetailsQuery, MovieDetails>,
    private val videoMapper: Mapper<VideoTable, Video>
) : MovieRepository {

    private val movieDao = database.getMovieDao()
    private val genreDao = database.getGenreDao()
    private val movieGenreDao = database.getMovieGenreDao()
    private val videoDao = database.getVideoDao()

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
        delay(1000)
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