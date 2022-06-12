package com.example.themoviedatabase.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.themoviedatabase.database.table.MovieGenreTable


@Dao
interface MovieGenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieGenres(movieGenres: List<MovieGenreTable>)
}