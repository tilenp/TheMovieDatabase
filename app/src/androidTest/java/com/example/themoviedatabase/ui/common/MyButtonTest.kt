package com.example.themoviedatabase.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MyButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun button_title_test() {
        // arrange
        val buttonText = "Click me"
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold { padding ->
                    MyButton(
                        modifier = Modifier.padding(padding),
                        title = buttonText,
                        onClick = { }
                    )
                }
            }
        }

        // just so you can see it on the phone
        Thread.sleep(1000)

        // assert
        composeTestRule.onNodeWithText(buttonText).assertIsDisplayed()
    }

    @Test
    fun button_click_test() {
        // arrange
        val buttonText = "Click me"
        var clicked = false
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold { padding ->
                    MyButton(
                        modifier = Modifier.padding(padding),
                        title = buttonText,
                        onClick = { clicked = true }
                    )
                }
            }
        }

        // just so you can see it on the phone
        Thread.sleep(1000)

        // act
        composeTestRule.onNodeWithText(buttonText).performClick()

        // assert
        Assert.assertTrue(clicked)
    }
}