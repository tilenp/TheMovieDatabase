package com.example.themoviedatabase.model.domain

import com.example.themoviedatabase.utils.UIValue
import com.example.themoviedatabase.utils.UIPlural
import com.example.themoviedatabase.utils.UIText

data class MovieDetails(
    val movieId: Long = 0,
    val title: UIText = UIText(),
    val backdropPath: ImagePath = ImagePath(),
    val rating: UIValue<Float> = UIValue(0f),
    val ratingCount: UIPlural = UIPlural(),
    val genres: String = "",
    val overview: UIText = UIText(),
    val releaseDate: String = "",
    val runtimeIcon: ImagePath = ImagePath(),
    val runtime: String = ""
)