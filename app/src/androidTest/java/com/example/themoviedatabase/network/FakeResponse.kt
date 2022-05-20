package com.example.themoviedatabase.network

data class FakeResponse<T>(
    val code: Int = 200,
    val body: T? = null,
    val testLoading: Boolean = false
)