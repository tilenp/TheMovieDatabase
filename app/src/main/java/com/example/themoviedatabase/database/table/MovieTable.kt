package com.example.themoviedatabase.database.table

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["movieId"], unique = true)
    ]
)
data class MovieTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val movieId: Long = 0L,
    val title: String = "",
    val popularity: Float = 0f,
    val overview: String = "",
    val ratingCount: Long = 0L,
    val rating: Float = 0f,
    val releaseDate: String = ""
)