package com.example.themoviedatabase.ui.movie_details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import com.example.themoviedatabase.utils.UIPlural
import com.example.themoviedatabase.utils.UIText
import com.example.themoviedatabase.utils.UIValue
import com.example.themoviedatabase.utils.thousandFormat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewTest {

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
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = null,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("LoadingView").assertIsDisplayed()
    }

    @Test
    fun backdrop_image_test() = runTest {
        val movieDetails = MovieDetails(
            backdropPath = ImagePath("/1Ds7xy7ILo8u2WWxdnkJth1jQVT.jpg")
        )

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = movieDetails,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("BackdropImage").assertIsDisplayed()
    }

    @Test
    fun back_button_window_size_compact_test() = runTest {
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = MovieDetails(),
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("BackdropIconButton").assertIsDisplayed()
    }

    @Test
    fun back_button_window_size_expanded_test() = runTest {
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Expanded,
                        modifier = Modifier.padding(padding),
                        movieDetails = MovieDetails(),
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("BackdropIconButton").assertDoesNotExist()
    }

    @Test
    fun back_button_click_test() = runTest {
        var clicked = false

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = MovieDetails(),
                        onBackButtonClicked = { clicked = true }
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("BackdropIconButton").performClick()
        assertTrue(clicked)
    }

    @Test
    fun movie_title_test() = runTest {
        val movieDetails = MovieDetails(
            title = UIText("The Lost City")
        )

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = movieDetails,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieTitleThe Lost City").assertIsDisplayed()
    }

    @Test
    fun rating_test() = runTest {
        val movieDetails = MovieDetails(
            rating = UIValue(
                value = 6.7f,
                formattedValue = "6.7"
            )
        )

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = movieDetails,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("RatingView6.7").assertIsDisplayed()
    }

    @Test
    fun rating_count_test() = runTest {
        val movieDetails = MovieDetails(
            ratingCount = UIPlural(
                pluralId = R.plurals.rating_count_format,
                formatArgs = 1276L.thousandFormat(),
                count = 1276L
            )
        )

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = movieDetails,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("RatingCount(1.3 k reviews)").assertIsDisplayed()
    }

    @Test
    fun genres_test() = runTest {
        val movieDetails = MovieDetails(
            genres = "Action, Comedy"
        )
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = movieDetails,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieGenresAction, Comedy").assertIsDisplayed()
    }

    @Test
    fun release_date_test() = runTest {
        val movieDetails = MovieDetails(
            releaseDate = "release date"
        )
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = movieDetails,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ReleaseDaterelease date").assertIsDisplayed()
    }

    @Test
    fun runtime_test() = runTest {
        val movieDetails = MovieDetails(
            runtime = "runtime"
        )
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = movieDetails,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieRuntimeruntime").assertIsDisplayed()
    }

    @Test
    fun overview_title_test() = runTest {
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = MovieDetails(),
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieOverviewTitle").assertIsDisplayed()
    }

    @Test
    fun overview_body_test() = runTest {
        val movieDetails = MovieDetails(
            overview = UIText("A reclusive romance novelist...")
        )

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState
                ) { padding ->
                    MovieDetailsView(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        movieDetails = movieDetails,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieOverviewA reclusive romance novelist...").assertIsDisplayed()
    }
}