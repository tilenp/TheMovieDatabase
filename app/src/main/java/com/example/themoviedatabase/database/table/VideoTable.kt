package com.example.themoviedatabase.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoTable (
    @PrimaryKey
    val id: String = "",
    val key: String = "",
    val itemId: Long = 0L
)