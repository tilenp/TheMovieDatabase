package com.example.themoviedatabase.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.themoviedatabase.R
import org.junit.Assert.assertEquals
import org.junit.Test

class UIDataTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun ui_text_string_test() {
        // arrange
        val string = "string"
        val uiText = UIText(string = string)

        // act
        val result = uiText.asString(context)

        // assert
        assertEquals(string, result)
    }

    @Test
    fun ui_text_string_id_test() {
        // arrange
        val stringId = R.string.app_name
        val uiText = UIText(stringId = stringId)

        // act
        val result = uiText.asString(context)

        // assert
        assertEquals(context.getString(stringId), result)
    }

    @Test
    fun ui_plural_one_test() {
        // arrange
        val pluralId = R.plurals.rating_count_format
        val formatArgs = "1"
        val count = 1L
        val uiPlural = UIPlural(
            pluralId = pluralId,
            formatArgs = formatArgs,
            count = count
        )

        // act
        val result = uiPlural.asString(context)

        // assert
        val text = context.resources.getQuantityString(pluralId, count.toInt(), formatArgs)
        assertEquals(text, result)
    }

    @Test
    fun ui_plural_two_test() {
        // arrange
        val pluralId = R.plurals.rating_count_format
        val formatArgs = "2"
        val count = 2L
        val uiPlural = UIPlural(
            pluralId = pluralId,
            formatArgs = formatArgs,
            count = count
        )

        // act
        val result = uiPlural.asString(context)

        // assert
        val text = context.resources.getQuantityString(pluralId, count.toInt(), formatArgs)
        assertEquals(text, result)
    }
}