package com.example.themoviedatabase.model.dto

data class ResponseDTO<T>(
    val id: Long?,
    val results: List<T>?
)