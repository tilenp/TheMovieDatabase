package com.example.themoviedatabase.database.dao

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.room.*
import com.example.themoviedatabase.database.query.*
import com.example.themoviedatabase.database.table.MovieTable
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovies(movies: List<MovieTable>)

    @Query("""
        SELECT
            MovieTable.movieId,
            MovieTable.title,
            MovieTable.rating,
            ImagePathTable.path as imagePath
        FROM MovieTable
        JOIN ImagePathTable ON MovieTable.movieId = ImagePathTable.itemId
        ORDER BY MovieTable.id ASC
    """)
    abstract fun getPopularMovies(): PagingSource<Int, MovieSummaryQuery>

    @Query("""
        SELECT
            MovieTable.movieId,
            MovieTable.title,
            MovieTable.rating,
            ImagePathTable.path as imagePath
        FROM MovieTable
        JOIN SimilarMovieTable ON MovieTable.movieId = SimilarMovieTable.similarMovieId
        JOIN ImagePathTable ON MovieTable.movieId = ImagePathTable.itemId
        WHERE SimilarMovieTable.movieId = :movieId
        GROUP BY MovieTable.movieId
    """)
    abstract fun getSimilarMovies(movieId: Long): Flow<List<MovieSummaryQuery>>

    @Query("DELETE FROM MovieTable")
    abstract suspend fun deleteMovies()

    /**
     * need to update the entry manually, until The Movie Database sorting bug is fixed
     */

    suspend fun updateMovie(movie: MovieTable) {
        updateMovie(
            movie.title,
            movie.popularity,
            movie.overview,
            movie.ratingCount,
            movie.rating,
            movie.releaseDate,
            movie.runtime,
            movie.movieId
        )
    }

    @VisibleForTesting
    @Query("""
        UPDATE MovieTable
        SET title = :title,
            popularity = :popularity,
            overview = :overview,
            ratingCount = :ratingCount,
            rating = :rating,
            releaseDate = :releaseDate,
            runtime = :runtime
        WHERE movieId = :movieId
    """)
    abstract suspend fun updateMovie(
        title: String,
        popularity: Float,
        overview: String,
        ratingCount: Long,
        rating: Float,
        releaseDate: String,
        runtime: Int,
        movieId: Long
    )

    @VisibleForTesting
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMovie(movie: MovieTable): Long

    @Transaction
    @Query("""
        SELECT * 
        FROM MovieTable 
        WHERE MovieTable.movieId = :movieId
    """)
    abstract fun getMovieDetailsWithId(movieId: Long): Flow<MovieDetailsQuery>

    suspend fun upsert(movies: List<MovieTable>) {
        movies.forEach { movie ->
            if (insertMovie(movie) == -1L) {
                updateMovie(movie)
            }
        }
    }
}