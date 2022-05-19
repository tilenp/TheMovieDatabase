package com.example.themoviedatabase.database.query

import androidx.room.Embedded
import androidx.room.Relation
import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.database.table.MovieTable

data class MovieSummaryQuery (
    @Embedded
    val movieTable: MovieTable,

    @Relation(
        parentColumn = "id",
        entityColumn = "itemId"
    )
    val imagePaths: List<ImagePathTable>?
)