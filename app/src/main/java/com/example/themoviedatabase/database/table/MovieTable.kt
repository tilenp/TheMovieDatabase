package com.example.themoviedatabase.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieTable(
    @PrimaryKey
    val id: Long = 0L,
    val title: String = "",
    val popularity: Float = 0f,
    val overview: String = "",
    val rating: Float = 0f
)