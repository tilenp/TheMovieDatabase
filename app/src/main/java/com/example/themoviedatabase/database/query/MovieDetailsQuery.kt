package com.example.themoviedatabase.database.query

import androidx.room.Embedded
import androidx.room.Relation
import com.example.themoviedatabase.database.table.BackdropImageTable
import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.database.table.MovieTable

data class MovieDetailsQuery (
    @Embedded
    val movieTable: MovieTable = MovieTable(),
    @Relation(
        entity = ImagePathTable::class,
        parentColumn = "movieId",
        entityColumn = "itemId",
        projection = ["path"]
    )
    val posterPaths: List<String>? = null,
    @Relation(
        entity = BackdropImageTable::class,
        parentColumn = "movieId",
        entityColumn = "itemId",
        projection = ["path"]
    )
    val backdropPaths: List<String>? = null
)