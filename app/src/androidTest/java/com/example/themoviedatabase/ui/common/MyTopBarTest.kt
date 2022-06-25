package com.example.themoviedatabase.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import org.junit.Rule
import org.junit.Test

class MyTopBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun app_bar_title_test() {
        // arrange
        val title = "TheMovieDatabase"
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold(
                    topBar = { MyTopBar(title = title) }
                ) { padding -> Surface(modifier = Modifier.padding(padding)) {} }
            }
        }

        // assert
        composeTestRule.onNodeWithText(title).assertIsDisplayed()
    }
}