package com.example.themoviedatabase.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImagePathTable (
    @PrimaryKey
    val path: String = "",
    val itemId: Long = 0L
)