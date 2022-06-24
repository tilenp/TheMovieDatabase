package com.example.themoviedatabase.ui.movies_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import com.example.themoviedatabase.utils.UIText
import com.example.themoviedatabase.utils.UIValue
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MoviesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun poster_test() {
        val movies = listOf(MovieSummary(movieId = 1), MovieSummary(movieId = 2))

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = flowOf(PagingData.from(movies)).collectAsLazyPagingItems()
                Scaffold { padding ->
                    MovieListContent(
                        columns = 2,
                        modifier = Modifier.padding(padding),
                        pagesMovies = pagedMovies,
                        onMovieClick = {},
                        getErrorMessageId = { R.string.Network_not_available }
                    )
                }
            }
        }

        // movie 1
        composeTestRule
            .onNode(
                hasTestTag("MovieImage1") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 2
        composeTestRule
            .onNode(
                hasTestTag("MovieImage2") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()
    }

    @Test
    fun movie_rating_test() {
        val movies = listOf(
            MovieSummary(rating = UIValue(value = 8.8f, formattedValue = "8.8")),
            MovieSummary(rating = UIValue(value = 10.0f, formattedValue = "10.0"))
        )

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = flowOf(PagingData.from(movies)).collectAsLazyPagingItems()
                Scaffold { padding ->
                    MovieListContent(
                        columns = 2,
                        modifier = Modifier.padding(padding),
                        pagesMovies = pagedMovies,
                        onMovieClick = {},
                        getErrorMessageId = { R.string.Network_not_available }
                    )
                }
            }
        }

        // movie 1
        composeTestRule
            .onNode(
                hasTestTag("RatingView8.8") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 2
        composeTestRule
            .onNode(
                hasTestTag("RatingView10.0") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()
    }

    @Test
    fun movie_title_test() {
        val movies = listOf(
            MovieSummary(title = UIText("title1")),
            MovieSummary(title = UIText("title2"))
        )

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = flowOf(PagingData.from(movies)).collectAsLazyPagingItems()
                Scaffold { padding ->
                    MovieListContent(
                        columns = 2,
                        modifier = Modifier.padding(padding),
                        pagesMovies = pagedMovies,
                        onMovieClick = {},
                        getErrorMessageId = { R.string.Network_not_available }
                    )
                }
            }
        }

        // movie 1
        composeTestRule
            .onNode(
                hasTestTag("MovieTitletitle1") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 2
        composeTestRule
            .onNode(
                hasTestTag("MovieTitletitle2") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()
    }

    @Test
    fun movie_click_test() {
        val movies = listOf(MovieSummary(movieId = 1), MovieSummary(movieId = 2))
        var clickedId: Long? = null

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = flowOf(PagingData.from(movies)).collectAsLazyPagingItems()
                Scaffold { padding ->
                    MovieListContent(
                        columns = 2,
                        modifier = Modifier.padding(padding),
                        pagesMovies = pagedMovies,
                        onMovieClick = { clickedId = it },
                        getErrorMessageId = { R.string.Network_not_available }
                    )
                }
            }
        }

        composeTestRule
            .onNode(
                hasTestTag("MovieImage1") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).performClick()
        assertEquals(1L, clickedId)
    }
}