package com.example.themoviedatabase.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class LongExtensionsTest {

    @Test
    fun below_thousand_test() {
        // assert
        assertEquals("999", 999L.thousandFormat())
    }

    @Test
    fun thousand_test() {
        // assert
        assertEquals("1,2 k", 1234L.thousandFormat())
    }

    @Test
    fun ten_thousand_test() {
        // assert
        assertEquals("12,3 k", 12345L.thousandFormat())
    }

    @Test
    fun hundred_thousand_test() {
        // assert
        assertEquals("123,5 k", 123456L.thousandFormat())
    }

    @Test
    fun million_test() {
        // assert
        assertEquals("1,2 M", 1234567L.thousandFormat())
    }

    @Test
    fun ten_million_test() {
        // assert
        assertEquals("12,3 M", 12345678L.thousandFormat())
    }

    @Test
    fun hundred_million_test() {
        // assert
        assertEquals("123,5 M", 123456789L.thousandFormat())
    }

    @Test
    fun billion_test() {
        // assert
        assertEquals("1,2 B", 1234567890L.thousandFormat())
    }
}