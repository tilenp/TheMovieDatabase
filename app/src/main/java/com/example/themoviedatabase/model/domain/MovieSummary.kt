package com.example.themoviedatabase.model.domain

import com.example.themoviedatabase.utils.UIValue
import com.example.themoviedatabase.utils.UIText

data class MovieSummary(
    val movieId: Long = 0,
    val title: UIText = UIText(),
    val posterPath: ImagePath = ImagePath(),
    val rating: UIValue<Float> = UIValue(0f)
)
