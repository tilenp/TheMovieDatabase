package com.example.themoviedatabase.utils

import com.example.themoviedatabase.R
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class ErrorMessageHandlerTest {

    @Test
    fun io_exception_test() {
        // arrange
        val exception = IOException()
        val errorMessageHandler = ErrorMessageHandler()

        // act
        val result = errorMessageHandler.getExceptionMessage(exception)

        // assert
        assertEquals(R.string.Network_not_available, result)
    }

    @Test
    fun http_exception_401_test() {
        // arrange
        val exception: HttpException = mockk()
        every { exception.code() } returns 401
        val errorMessageHandler = ErrorMessageHandler()

        // act
        val result = errorMessageHandler.getExceptionMessage(exception)

        // assert
        assertEquals(R.string.Authorization_error, result)
    }

    @Test
    fun http_exception_500_test() {
        // arrange
        val exception: HttpException = mockk()
        every { exception.code() } returns 500
        val errorMessageHandler = ErrorMessageHandler()

        // act
        val result = errorMessageHandler.getExceptionMessage(exception)

        // assert
        assertEquals(R.string.Server_error, result)
    }

    @Test
    fun unknown_exception_test() {
        // arrange
        val exception = Throwable()
        val errorMessageHandler = ErrorMessageHandler()

        // act
        val result = errorMessageHandler.getExceptionMessage(exception)

        // assert
        assertEquals(R.string.Unknown_error, result)
    }
}