package com.example.themoviedatabase.database.query

import androidx.room.Embedded
import androidx.room.Relation
import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.database.table.MovieTable

data class MovieSummaryQuery(
    @Embedded
    val movieTable: MovieTable = MovieTable(),

    @Relation(
        parentColumn = "movieId",
        entityColumn = "itemId"
    )
    val imagePaths: List<ImagePathTable>? = null
)