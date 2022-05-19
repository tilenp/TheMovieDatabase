package com.example.themoviedatabase.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviedatabase.database.table.MoviePagingKeysTable

@Dao
interface MoviePagingKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagingKeys(pagingKeys: List<MoviePagingKeysTable>)

    @Query("""
        SELECT * 
        FROM MoviePagingKeysTable 
        WHERE MoviePagingKeysTable.movieId = :movieId
    """)
    suspend fun getPagingKeysForMovieId(movieId: Long): MoviePagingKeysTable?
}