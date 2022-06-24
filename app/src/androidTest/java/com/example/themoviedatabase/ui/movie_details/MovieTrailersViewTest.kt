package com.example.themoviedatabase.ui.movie_details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieTrailersViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun show_loading_test() = runTest {
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieTrailersView(
                        modifier = Modifier.padding(padding),
                        videos = null,
                        onVideoClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("LoadingView").assertIsDisplayed()
    }

    @Test
    fun section_title_test() = runTest {
        val videos = listOf(Video(), Video())
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieTrailersView(
                        modifier = Modifier.padding(padding),
                        videos = videos,
                        onVideoClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("CarouselViewTrailers").assertIsDisplayed()
    }

    @Test
    fun empty_trailers_list_test() = runTest {
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieTrailersView(
                        modifier = Modifier.padding(padding),
                        videos = emptyList(),
                        onVideoClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("LoadingView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("CarouselViewTrailers").assertDoesNotExist()
    }
}