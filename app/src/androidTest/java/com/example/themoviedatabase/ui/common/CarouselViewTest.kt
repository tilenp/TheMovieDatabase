package com.example.themoviedatabase.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import org.junit.Rule
import org.junit.Test

class CarouselViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun title_test() {
        val title = "title"

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val spacingS = dimensionResource(R.dimen.spacing_s)
                val paddingValues = PaddingValues(start = spacingS, end = spacingS)
                val horizontalArrangement = Arrangement.spacedBy(spacingS)
                val imageSize = dimensionResource(R.dimen.carousel_image_size)
                Scaffold { padding ->
                    CarouselView(
                        modifier = Modifier.padding(padding),
                        title = title,
                        paddingValues = paddingValues,
                        horizontalArrangement = horizontalArrangement,
                        list = emptyList<MovieSummary>(),
                        itemView = { movie ->
                            MovieItemView(
                                modifier = Modifier.height(imageSize),
                                movieId = movie.movieId,
                                title = movie.title,
                                posterPath = movie.posterPath,
                                rating = movie.rating.formattedValue,
                                onMovieClick = {}
                            )
                        }
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("CarouselViewtitle").assertIsDisplayed()
    }

    @Test
    fun items_test() {
        val movies = listOf(MovieSummary(movieId = 1L), MovieSummary(movieId = 2L))

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val spacingS = dimensionResource(R.dimen.spacing_s)
                val paddingValues = PaddingValues(start = spacingS, end = spacingS)
                val horizontalArrangement = Arrangement.spacedBy(spacingS)
                val imageSize = dimensionResource(R.dimen.carousel_image_size)
                Scaffold { padding ->
                    CarouselView(
                        modifier = Modifier.padding(padding),
                        title = "",
                        paddingValues = paddingValues,
                        horizontalArrangement = horizontalArrangement,
                        list = movies,
                        itemView = { movie ->
                            MovieItemView(
                                modifier = Modifier.height(imageSize)
                                    .testTag("MovieItem${movie.movieId}"),
                                movieId = movie.movieId,
                                title = movie.title,
                                posterPath = movie.posterPath,
                                rating = movie.rating.formattedValue,
                                onMovieClick = {}
                            )
                        }
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieItem1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("MovieItem2").assertIsDisplayed()
    }
}