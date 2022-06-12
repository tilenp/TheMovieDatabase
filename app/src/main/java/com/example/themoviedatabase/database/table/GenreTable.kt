package com.example.themoviedatabase.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreTable (
    @PrimaryKey
    val genreId: Long = 0L,
    val name: String = ""
)