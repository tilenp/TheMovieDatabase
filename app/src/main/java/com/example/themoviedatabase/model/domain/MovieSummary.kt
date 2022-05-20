package com.example.themoviedatabase.model.domain

import com.example.themoviedatabase.utils.UIText

data class MovieSummary(
    val movieId: Long,
    val title: UIText,
    val popularity: Float,
    val overview: UIText,
    val posterPath: ImagePath,
    val rating: Float
)
