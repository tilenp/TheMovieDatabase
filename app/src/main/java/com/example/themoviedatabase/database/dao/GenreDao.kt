package com.example.themoviedatabase.database.dao

import androidx.annotation.VisibleForTesting
import androidx.room.*
import com.example.themoviedatabase.database.table.GenreTable

@Dao
abstract class GenreDao {

    @VisibleForTesting
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertGenre(genre: GenreTable): Long

    @VisibleForTesting
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateGenres(genre: GenreTable)

    @Transaction
    open suspend fun upsert(genres: List<GenreTable>) {
        genres.forEach { genre ->
            if (insertGenre(genre) == -1L) {
                updateGenres(genre)
            }
        }
    }
}