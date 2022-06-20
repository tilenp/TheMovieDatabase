package com.example.themoviedatabase.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.themoviedatabase.database.table.SimilarMovieTable

@Dao
interface SimilarMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSimilarMovies(similarMovies: List<SimilarMovieTable>)
}