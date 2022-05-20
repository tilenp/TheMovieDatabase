package com.example.themoviedatabase.network

import java.io.IOException

data class FakeResponse<T>(
    val code: Int = 200,
    val body: T? = null,
    val testLoading: Boolean = false,
    val ioException: IOException? = null
)