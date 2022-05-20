package com.example.themoviedatabase.model.dto

data class MovieDTO(
    val id: Long = 0,
    val posterPath: String? = null,
    val name: String? = null,
    val overview: String? = null,
    val popularity: Float? = null,
    val voteAverage: Float? = null,
)