package com.example.themoviedatabase.database.dao

import androidx.room.*
import com.example.themoviedatabase.database.table.GenreTable

@Dao
abstract class GenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertGenre(genre: GenreTable): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun updateGenres(genre: GenreTable)

    @Transaction
    open suspend fun upsert(genres: List<GenreTable>) {
        genres.forEach { genre ->
            if (insertGenre(genre) == -1L) {
                updateGenres(genre)
            }
        }
    }
}