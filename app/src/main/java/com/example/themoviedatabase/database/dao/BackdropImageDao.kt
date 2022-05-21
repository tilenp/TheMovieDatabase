package com.example.themoviedatabase.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviedatabase.database.table.BackdropImageTable

@Dao
interface BackdropImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBackdropImages(backdropImages: List<BackdropImageTable>)

    @Query("DELETE FROM BackdropImageTable")
    suspend fun deleteBackdropImages()
}