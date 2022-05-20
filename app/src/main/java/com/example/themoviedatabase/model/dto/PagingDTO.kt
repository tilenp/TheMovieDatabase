package com.example.themoviedatabase.model.dto

data class PagingDTO<T> (
    val page: Int = 0,
    val totalPages: Int = 0,
    val results: List<T> = emptyList(),
)