package com.example.themoviedatabase.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.MovieTable
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovies(movies: List<MovieTable>)

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT * 
        FROM MovieTable 
        ORDER BY MovieTable.id ASC
    """)
    abstract fun getPopularMovies(): PagingSource<Int, MovieSummaryQuery>

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
    protected abstract suspend fun updateMovie(
        title: String,
        popularity: Float,
        overview: String,
        ratingCount: Long,
        rating: Float,
        releaseDate: String,
        runtime: Int,
        movieId: Long
    )

    @Transaction
    @Query("""
        SELECT * 
        FROM MovieTable 
        WHERE MovieTable.movieId = :movieId
    """)
    abstract fun getMovieDetailsWithId(movieId: Long): Flow<MovieDetailsQuery>
}