package com.example.themoviedatabase.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.themoviedatabase.database.table.ImagePathTable

@Dao
interface ImagePathDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImagePaths(imagePaths: List<ImagePathTable>)
}