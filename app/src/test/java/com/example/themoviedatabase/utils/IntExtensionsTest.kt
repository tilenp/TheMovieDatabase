package com.example.themoviedatabase.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class IntExtensionsTest {

    @Test
    fun below_1_hour_test() {
        // assert
        assertEquals("0:45", 45.toHourMinutes())
    }

    @Test
    fun above_1_hour_test() {
        // assert
        assertEquals("1:45", 105.toHourMinutes())
    }

    @Test
    fun above_2_hours_test() {
        // assert
        assertEquals("2:45", 165.toHourMinutes())
    }

    @Test
    fun above_10_hours_test() {
        // assert
        assertEquals("10:45", 645.toHourMinutes())
    }
}