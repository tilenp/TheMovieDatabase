package com.example.themoviedatabase.model

data class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
)