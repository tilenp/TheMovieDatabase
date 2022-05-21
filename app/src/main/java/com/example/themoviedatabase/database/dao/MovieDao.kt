package com.example.themoviedatabase.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.MovieTable
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieTable>)

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT * 
        FROM MovieTable 
        ORDER BY MovieTable.id ASC
    """)
    fun getPopularMovies(): PagingSource<Int, MovieSummaryQuery>

    @Query("DELETE FROM MovieTable")
    suspend fun deleteMovies()

    @Transaction
    @Query("""
        SELECT * 
        FROM MovieTable 
        WHERE MovieTable.movieId = :movieId
    """)
    fun getMovieDetailsWithId(movieId: Long): Flow<MovieDetailsQuery>
}