package com.example.themoviedatabase.ui.movies_screen

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.example.themoviedatabase.R
import com.example.themoviedatabase.dagger.FakeApplication
import com.example.themoviedatabase.database.MovieDatabase
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
import com.example.themoviedatabase.network.FakeMovieApi
import com.example.themoviedatabase.network.FakeResponse
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import com.example.themoviedatabase.utils.DispatcherProvider
import com.example.themoviedatabase.utils.ErrorMessageHandler
import com.example.themoviedatabase.utils.FakeDispatcherProvider
import com.example.themoviedatabase.utils.FileReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.lang.reflect.Type
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var fakeMovieApi: FakeMovieApi

    @Inject
    lateinit var database: MovieDatabase

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var movieRepository: MovieRepository

    @Inject
    lateinit var errorMessageHandler: ErrorMessageHandler

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var fakeDispatcherProvider: FakeDispatcherProvider
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUp() {
        (context as FakeApplication).provideAppComponent().inject(this)
        fakeDispatcherProvider = dispatcherProvider as FakeDispatcherProvider
        viewModel = MoviesViewModel(
            movieRepository = movieRepository,
            errorMessageHandler = errorMessageHandler,
            dispatcherProvider = fakeDispatcherProvider
        )
    }

    @After
    fun tearDown() {
        database.clearAllTables()
    }

    private fun enqueueResponse(code: Int, fileName: String, testLoading: Boolean) {
        val json = FileReader.readFile(context, fileName)
        val bodyType: Type = object : TypeToken<PagingDTO<MovieDTO>>() {}.type
        val body: PagingDTO<MovieDTO> = gson.fromJson(json, bodyType)
        fakeMovieApi.enqueue(FakeResponse(code, body, testLoading))
    }

    @Test
    fun top_app_bar_test() {
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 200)
        fakeMovieApi.enqueue(testResponse)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold { padding ->
                    MoviesScreen(
                        modifier = Modifier.padding(padding),
                        viewModel = viewModel,
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MyTopBar").assertIsDisplayed()
    }

    @Test
    fun refresh_loading_test() {
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 200, testLoading = true)
        fakeMovieApi.enqueue(testResponse)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold { padding ->
                    MoviesScreen(
                        modifier = Modifier.padding(padding),
                        viewModel = viewModel,
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun refresh_error_message_test() {
        val errorMessage = "network not available"
        val ioException = IOException(errorMessage)
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 401, ioException = ioException)
        fakeMovieApi.enqueue(testResponse)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold { padding ->
                    MoviesScreen(
                        modifier = Modifier.padding(padding),
                        viewModel = viewModel,
                    )
                }
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.Click_to_retry)).assertIsDisplayed()
    }

    @Test
    fun refresh_error_retry_button_test() {
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 401)
        fakeMovieApi.enqueue(testResponse)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold { padding ->
                    MoviesScreen(
                        modifier = Modifier.padding(padding),
                        viewModel = viewModel,
                    )
                }
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.Click_to_retry)).assertIsDisplayed()
    }

    @Test
    fun poster_test() {
        enqueueResponse(code = 200, fileName = "movies_popular_desc_page_1_200", false)
        enqueueResponse(code = 200, fileName = "movies_popular_desc_end_reached_200", false)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold { padding ->
                    MoviesScreen(
                        modifier = Modifier.padding(padding),
                        viewModel = viewModel,
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieImage752623").assertIsDisplayed()
    }

    @Test
    fun movie_rating_test() {
        enqueueResponse(code = 200, fileName = "movies_popular_desc_page_1_200", false)
        enqueueResponse(code = 200, fileName = "movies_popular_desc_end_reached_200", false)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold { padding ->
                    MoviesScreen(
                        modifier = Modifier.padding(padding),
                        viewModel = viewModel,
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieRating752623").assertIsDisplayed()
    }

    @Test
    fun movie_title_test() {
        enqueueResponse(code = 200, fileName = "movies_popular_desc_page_1_200", false)
        enqueueResponse(code = 200, fileName = "movies_popular_desc_end_reached_200", false)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                Scaffold { padding ->
                    MoviesScreen(
                        modifier = Modifier.padding(padding),
                        viewModel = viewModel,
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieTitle752623").assertIsDisplayed()
    }
}