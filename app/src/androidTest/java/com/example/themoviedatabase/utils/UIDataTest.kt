package com.example.themoviedatabase.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.themoviedatabase.R
import org.junit.Assert.assertEquals
import org.junit.Test

class UIDataTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun string_test() {
        // arrange
        val string = "string"
        val uiText = UIText(string = string)

        // act
        val result = uiText.asString(context)

        // assert
        assertEquals(string, result)
    }

    @Test
    fun string_id_test() {
        // arrange
        val stringId = R.string.app_name
        val uiText = UIText(stringId = stringId)

        // act
        val result = uiText.asString(context)

        // assert
        assertEquals(context.getString(stringId), result)
    }
}