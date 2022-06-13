package com.example.themoviedatabase.database.dao

import androidx.room.*
import com.example.themoviedatabase.database.table.VideoTable
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoTable>)

    @Transaction
    @Query("""
        SELECT * 
        FROM VideoTable 
        WHERE VideoTable.itemId = :itemId
    """)
    fun getVideosWithItemId(itemId: Long): Flow<List<VideoTable>>
}