package com.example.themoviedatabase.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import org.junit.Rule
import org.junit.Test

class LoadingViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun visibility_test() {
        // arrange
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold { padding ->
                    LoadingView(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding))
                }
            }
        }

        // assert
        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }
}