package com.example.themoviedatabase.database.query

data class MovieSummaryQuery(
    val movieId: Long = 0L,
    val title: String = "",
    val rating: Float = 0f,
    val imagePath: String? = null
)