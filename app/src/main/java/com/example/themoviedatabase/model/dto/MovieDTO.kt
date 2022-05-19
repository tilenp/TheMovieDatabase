package com.example.themoviedatabase.model.dto

data class MovieDTO(
    val id: Long,
    val posterPath: String?,
    val name: String?,
    val overview: String?,
    val popularity: Float?,
    val voteAverage: Float?,
)