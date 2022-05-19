package com.example.themoviedatabase.utils

import com.example.themoviedatabase.R
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMessageHandler @Inject constructor() {

    fun getExceptionMessage(throwable: Throwable?): Int {
        return  when (throwable) {
            is IOException -> R.string.Network_not_available
            is HttpException -> getHttpExceptionMessage(throwable)
            else -> R.string.Unknown_error
        }
    }

    private fun getHttpExceptionMessage(httpException: HttpException): Int {
        return when (httpException.code()) {
            401 -> R.string.Authorization_error
            in 500..599 -> R.string.Server_error
            else -> R.string.Unknown_error
        }
    }
}