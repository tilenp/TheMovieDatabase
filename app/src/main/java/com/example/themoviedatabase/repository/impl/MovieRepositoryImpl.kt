package com.example.themoviedatabase.repository.impl

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.example.themoviedatabase.database.MovieDatabase
import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.*
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.network.MovieRequestQuery
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.service.MovieDetailsService
import com.example.themoviedatabase.service.MovieService
import com.example.themoviedatabase.service.SimilarMoviesService
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
    private val similarMoviesService: SimilarMoviesService,
    private val database: MovieDatabase,
    private val movieTableMapper: Mapper<MovieDTO, MovieTable>,
    private val backdropImageTableMapper: Mapper<MovieDTO, BackdropImageTable>,
    private val imagePathTableMapper: Mapper<MovieDTO, ImagePathTable>,
    private val genreTableMapper: Mapper<MovieDTO, List<GenreTable>>,
    private val movieGenreTableMapper: Mapper<MovieDTO, List<MovieGenreTable>>,
    private val movieSummaryMapper: Mapper<MovieSummaryQuery, MovieSummary>,
    private val movieDetailsMapper: Mapper<MovieDetailsQuery, MovieDetails>,
) : MovieRepository {

    private val backdropImageDao = database.getBackdropImageDao()
    private val imagePathDao = database.getImagePathDao()
    private val movieDao = database.getMovieDao()
    private val similarMoviesDao = database.getSimilarMoviesDao()
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

    override suspend fun updateMovieDetailsWithId(movieId: Long) {
        delay(1000)
        val movieDetailsDto = movieDetailsService.getMovieDetails(movieId)
//        throw Throwable("IO exception")
        database.withTransaction {
            backdropImageDao.insertBackdropImages(listOf(backdropImageTableMapper.map(movieDetailsDto)))
            imagePathDao.insertImagePaths(listOf(imagePathTableMapper.map(movieDetailsDto)))
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

    override suspend fun updateSimilarMoviesForId(movieId: Long, page: Int?) {
        val similarMovies = similarMoviesService.getSimilarMovies(movieId, page)
        database.withTransaction {
            backdropImageDao.insertBackdropImages(similarMovies.results.map { backdropImageTableMapper.map(it) })
            imagePathDao.insertImagePaths(similarMovies.results.map { imagePathTableMapper.map(it) })
            movieDao.upsert(similarMovies.results.map { movieTableMapper.map(it) })
            similarMoviesDao.insertSimilarMovies(similarMovies.results.map { SimilarMovieTable(movieId, it.id) })
        }
    }

    override fun getSimilarMoviesForId(movieId: Long): Flow<List<MovieSummary>> {
        return movieDao.getSimilarMovies(movieId)
            .filterNotNull()
            .map { similarMovies -> similarMovies.map { movieSummaryMapper.map(it) } }
    }
}