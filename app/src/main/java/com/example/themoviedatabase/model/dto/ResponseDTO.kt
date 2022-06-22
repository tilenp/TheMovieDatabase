package com.example.themoviedatabase.model.dto

data class ResponseDTO<T>(
    val id: Long? = null,
    val results: List<T>? = null
)