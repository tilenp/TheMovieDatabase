package com.example.themoviedatabase.model.domain

import com.example.themoviedatabase.utils.UIText

data class MovieDetails (
    val movieId: Long = 0,
    val title: UIText = UIText(),
    val posterPath: ImagePath = ImagePath(),
    val backdropPath: ImagePath = ImagePath(),
    val rating: Float = 0f,
    val overview: UIText = UIText()
)