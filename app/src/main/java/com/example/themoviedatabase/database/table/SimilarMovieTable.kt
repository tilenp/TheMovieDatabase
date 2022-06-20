package com.example.themoviedatabase.database.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["movieId", "similarMovieId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieTable::class,
            parentColumns = ["movieId"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MovieTable::class,
            parentColumns = ["movieId"],
            childColumns = ["similarMovieId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["similarMovieId"])
    ]
)
data class SimilarMovieTable (
    val movieId: Long = 0L,
    val similarMovieId: Long = 0L,
)