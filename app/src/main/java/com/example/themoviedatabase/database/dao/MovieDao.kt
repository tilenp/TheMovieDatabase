package com.example.themoviedatabase.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.MovieTable

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieTable>)

    @Transaction
    @Query("""
        SELECT * 
        FROM MovieTable 
        ORDER BY MovieTable.id ASC
    """)
    fun getPopularMovies(): PagingSource<Int, MovieSummaryQuery>

    @Query("DELETE FROM MovieTable")
    suspend fun deleteMovies()
}