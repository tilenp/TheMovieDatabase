package com.example.themoviedatabase.model.domain

import com.example.themoviedatabase.utils.UIText

data class MovieSummary(
    val movieId: Long = 0,
    val title: UIText = UIText(),
    val popularity: Float = 0f,
    val overview: UIText = UIText(),
    val posterPath: ImagePath = ImagePath(),
    val rating: Float = 0f
)
