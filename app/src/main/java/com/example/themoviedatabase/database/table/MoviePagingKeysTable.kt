package com.example.themoviedatabase.database.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MovieTable::class,
            parentColumns = ["movieId"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MoviePagingKeysTable(
    @PrimaryKey
    val movieId: Long = 0,
    val previousKey: Int? = null,
    val nextKey: Int? = null
)