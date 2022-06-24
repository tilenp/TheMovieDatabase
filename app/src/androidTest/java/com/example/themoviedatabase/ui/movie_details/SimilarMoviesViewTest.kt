package com.example.themoviedatabase.ui.movie_details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SimilarMoviesViewTest {

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
                    SimilarMoviesView(
                        modifier = Modifier.padding(padding),
                        movies = null,
                        onMovieClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("LoadingView").assertIsDisplayed()
    }

    @Test
    fun section_title_test() = runTest {
        val similarMovies = listOf(MovieSummary(), MovieSummary())
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    SimilarMoviesView(
                        modifier = Modifier.padding(padding),
                        movies = similarMovies,
                        onMovieClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("CarouselViewSimilar movies").assertIsDisplayed()
    }

    @Test
    fun empty_similar_movies_list_test() = runTest {
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    SimilarMoviesView(
                        modifier = Modifier.padding(padding),
                        movies = emptyList(),
                        onMovieClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("LoadingView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("CarouselViewSimilar movies").assertDoesNotExist()
    }
}