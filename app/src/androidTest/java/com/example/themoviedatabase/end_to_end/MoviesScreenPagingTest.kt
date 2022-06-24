package com.example.themoviedatabase.end_to_end

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.core.app.ApplicationProvider
import com.example.themoviedatabase.R
import com.example.themoviedatabase.cache.MovieCache
import com.example.themoviedatabase.dagger.FakeApplication
import com.example.themoviedatabase.database.MovieDatabase
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
import com.example.themoviedatabase.network.FakeMovieApi
import com.example.themoviedatabase.network.FakeResponse
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.ui.movies_screen.MovieListContent
import com.example.themoviedatabase.ui.movies_screen.MoviesViewModel
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import com.example.themoviedatabase.utils.DispatcherProvider
import com.example.themoviedatabase.utils.ErrorMessageHandler
import com.example.themoviedatabase.utils.FakeDispatcherProvider
import com.example.themoviedatabase.utils.FileReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import java.io.IOException
import java.lang.reflect.Type
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesScreenPagingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var fakeMovieApi: FakeMovieApi

    @Inject
    lateinit var database: MovieDatabase

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var movieCache: MovieCache

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
            movieCache = movieCache,
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
        fakeMovieApi.enqueueMovieSummaries(FakeResponse(code, body, testLoading))
    }

    @Test
    fun refresh_loading_test() {
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 200, testLoading = true)
        fakeMovieApi.enqueueMovieSummaries(testResponse)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = viewModel.movies.collectAsLazyPagingItems()
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

        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun refresh_error_test() {
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 400, throwable = IOException())
        fakeMovieApi.enqueueMovieSummaries(testResponse)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = viewModel.movies.collectAsLazyPagingItems()
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

        composeTestRule.onNodeWithTag("MoviesScreenErrorViewNetwork not available").assertIsDisplayed()
    }

    @Test
    fun refresh_retry_test() {
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 400, throwable = IOException())
        fakeMovieApi.enqueueMovieSummaries(testResponse)
        enqueueResponse(code = 200, fileName = "paging_2_movies_page_1_200", false)
        enqueueResponse(code = 200, fileName = "paging_2_movies_page_2_200", false)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = viewModel.movies.collectAsLazyPagingItems()
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

        composeTestRule.onNodeWithText(context.getString(R.string.Click_to_retry)).performClick()

        // movie 1
        composeTestRule
            .onNode(
                hasTestTag("MovieImage752623") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 2
        composeTestRule
            .onNode(
                hasTestTag("MovieImage675353") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 3
        composeTestRule
            .onNode(
                hasTestTag("MovieImage335787") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 4
        composeTestRule
            .onNode(
                hasTestTag("MovieImage639933") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()
    }

    @Test
    fun append_loading_test() {
        enqueueResponse(code = 200, fileName = "paging_2_movies_page_1_200", false)
        enqueueResponse(code = 200, fileName = "paging_2_movies_page_2_200", true)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = viewModel.movies.collectAsLazyPagingItems()
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
                hasTestTag("MovieImage752623") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 2
        composeTestRule
            .onNode(
                hasTestTag("MovieImage675353") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun append_test() {
        enqueueResponse(code = 200, fileName = "paging_2_movies_page_1_200", false)
        enqueueResponse(code = 200, fileName = "paging_2_movies_page_2_200", false)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = viewModel.movies.collectAsLazyPagingItems()
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
                hasTestTag("MovieImage752623") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 2
        composeTestRule
            .onNode(
                hasTestTag("MovieImage675353") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 3
        composeTestRule
            .onNode(
                hasTestTag("MovieImage335787") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 4
        composeTestRule
            .onNode(
                hasTestTag("MovieImage639933") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()
    }

    @Test
    fun append_error_test() {
        enqueueResponse(code = 200, fileName = "paging_2_movies_page_1_200", false)
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 400, throwable = IOException())
        fakeMovieApi.enqueueMovieSummaries(testResponse)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = viewModel.movies.collectAsLazyPagingItems()
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
                hasTestTag("MovieImage752623") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 2
        composeTestRule
            .onNode(
                hasTestTag("MovieImage675353") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        composeTestRule.onNodeWithTag("MoviesScreenErrorViewNetwork not available").assertIsDisplayed()
    }

    @Test
    fun append_retry_test() {
        enqueueResponse(code = 200, fileName = "paging_2_movies_page_1_200", false)
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 400, throwable = IOException())
        fakeMovieApi.enqueueMovieSummaries(testResponse)
        enqueueResponse(code = 200, fileName = "paging_2_movies_page_2_200", false)

        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val pagedMovies = viewModel.movies.collectAsLazyPagingItems()
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
                hasTestTag("MovieImage752623") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 2
        composeTestRule
            .onNode(
                hasTestTag("MovieImage675353") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 3
        composeTestRule
            .onNode(
                hasTestTag("MovieImage335787") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertDoesNotExist()

        // movie 4
        composeTestRule
            .onNode(
                hasTestTag("MovieImage639933") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertDoesNotExist()

        composeTestRule.onNodeWithText(context.getString(R.string.Click_to_retry)).performClick()

        // movie 1
        composeTestRule
            .onNode(
                hasTestTag("MovieImage752623") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 2
        composeTestRule
            .onNode(
                hasTestTag("MovieImage675353") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 3
        composeTestRule
            .onNode(
                hasTestTag("MovieImage335787") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // movie 4
        composeTestRule
            .onNode(
                hasTestTag("MovieImage639933") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()
    }
}