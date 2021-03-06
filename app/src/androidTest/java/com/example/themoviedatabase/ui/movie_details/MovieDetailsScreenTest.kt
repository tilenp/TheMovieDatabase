package com.example.themoviedatabase.ui.movie_details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun show_instructions_test() = runTest {
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        scaffoldState = scaffoldState,
                        modifier = Modifier.padding(padding),
                        uiState = MovieDetailsState.Instructions,
                        onBackButtonClicked = {},
                        onVideoClick = {},
                        onSnackbarActionPerformed = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ShowInstructions").assertIsDisplayed()
    }

    @Test
    fun show_content_test() = runTest {
        val uiState = MovieDetailsState.Builder().build()
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        scaffoldState = scaffoldState,
                        modifier = Modifier.padding(padding),
                        uiState = uiState,
                        onBackButtonClicked = {},
                        onVideoClick = {},
                        onSnackbarActionPerformed = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieDetailsView").assertIsDisplayed()
        composeTestRule.onNodeWithTag("MovieTrailersView").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SimilarMoviesView").assertIsDisplayed()
    }
}