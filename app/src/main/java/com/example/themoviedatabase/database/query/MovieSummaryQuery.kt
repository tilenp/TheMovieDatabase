package com.example.themoviedatabase.database.query

import androidx.room.Relation
import com.example.themoviedatabase.database.table.ImagePathTable

data class MovieSummaryQuery(
    val movieId: Long = 0L,
    val title: String = "",
    val rating: Float = 0f,
    @Relation(
        entity = ImagePathTable::class,
        parentColumn = "movieId",
        entityColumn = "itemId",
        projection = ["path"]
    )
    val imagePaths: List<String>? = null
)