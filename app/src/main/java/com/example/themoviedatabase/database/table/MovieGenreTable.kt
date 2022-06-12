package com.example.themoviedatabase.database.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["movieId", "genreId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieTable::class,
            parentColumns = ["movieId"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenreTable::class,
            parentColumns = ["genreId"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["genreId"])
    ]
)
data class MovieGenreTable (
    val movieId: Long = 0L,
    val genreId: Long = 0L,
)