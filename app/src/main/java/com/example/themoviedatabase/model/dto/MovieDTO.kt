package com.example.themoviedatabase.model.dto

data class MovieDTO(
    val id: Long = 0,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val title: String? = null,
    val overview: String? = null,
    val popularity: Float? = null,
    val voteCount: Long? = null,
    val voteAverage: Float? = null,
    val releaseDate: String? = null,
    val runtime: Int? = null,
    val genres: List<GenreDTO>? = null
)

data class GenreDTO(
    val id: Long? = 0,
    val name: String? = ""
)