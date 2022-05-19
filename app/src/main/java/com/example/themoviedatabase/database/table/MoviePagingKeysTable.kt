package com.example.themoviedatabase.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviePagingKeysTable(
    @PrimaryKey
    val itemId: Long = 0,
    val previousKey: Int? = null,
    val nextKey: Int? = null
)