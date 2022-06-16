package com.example.themoviedatabase.ui.movie_details

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.themoviedatabase.cache.MovieCache
import com.example.themoviedatabase.dagger.FakeApplication
import com.example.themoviedatabase.database.MovieDatabase
import com.example.themoviedatabase.database.table.BackdropImageTable
import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import com.example.themoviedatabase.utils.DispatcherProvider
import com.example.themoviedatabase.utils.FakeDispatcherProvider
import com.example.themoviedatabase.utils.FileReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertTrue
import java.lang.reflect.Type
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var movieTableMapper: Mapper<MovieDTO, MovieTable>

    @Inject
    lateinit var backdropImageTableMapper: Mapper<MovieDTO, BackdropImageTable>

    @Inject
    lateinit var database: MovieDatabase

    @Inject
    lateinit var movieCache: MovieCache

    @Inject
    lateinit var movieRepository: MovieRepository

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var fakeDispatcherProvider: FakeDispatcherProvider
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        (context as FakeApplication).provideAppComponent().inject(this)
        fakeDispatcherProvider = dispatcherProvider as FakeDispatcherProvider
        setUpData()
        setUpViewModel()
    }

    private fun setUpData() = runBlocking {
        val json = FileReader.readFile(context, "movie")
        val bodyType: Type = object : TypeToken<MovieDTO>() {}.type
        val movieDTO: MovieDTO = gson.fromJson(json, bodyType)
        val movieTable = movieTableMapper.map(movieDTO)
        val backdropImageTable = backdropImageTableMapper.map(movieDTO)

        database.getMovieDao().insertMovies(listOf(movieTable))
        database.getBackdropImageDao().insertBackdropImages(listOf(backdropImageTable))
    }

    private fun setUpViewModel() {
        viewModel = MovieDetailsViewModel(
            movieRepository = movieRepository,
            dispatcherProvider = fakeDispatcherProvider
        )
    }

    @After
    fun tearDown() {
        database.clearAllTables()
    }

    @Test
    fun show_instructions_test() = runTest {
        movieCache.setSelectedMovieId(-1)
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Instructions)
                Scaffold { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        uiState = uiState,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ShowInstructions").assertIsDisplayed()
    }

    @Test
    fun backdrop_image_test() = runTest {
        movieCache.setSelectedMovieId(752623)
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Instructions)
                Scaffold { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        uiState = uiState,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("BackdropImage").assertIsDisplayed()
    }

    @Test
    fun back_button_test() = runTest {
        movieCache.setSelectedMovieId(752623)
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Instructions)
                Scaffold { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        uiState = uiState,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("BackdropIconButton").assertIsDisplayed()
    }

    @Test
    fun back_button_click_test() = runTest {
        movieCache.setSelectedMovieId(752623)
        var clicked = false
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Instructions)
                Scaffold { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        uiState = uiState,
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
        movieCache.setSelectedMovieId(752623)
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Instructions)
                Scaffold { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        uiState = uiState,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieTitleThe Lost City").assertIsDisplayed()
    }

    @Test
    fun rating_test() = runTest {
        movieCache.setSelectedMovieId(752623)
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Instructions)
                Scaffold { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        uiState = uiState,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("RatingView6.7").assertIsDisplayed()
    }

    @Test
    fun rating_count_test() = runTest {
        movieCache.setSelectedMovieId(752623)
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Instructions)
                Scaffold { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        uiState = uiState,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("RatingCount(618 reviews)").assertIsDisplayed()
    }

    @Test
    fun overview_title_test() = runTest {
        movieCache.setSelectedMovieId(752623)
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Instructions)
                Scaffold { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        uiState = uiState,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieOverviewTitle").assertIsDisplayed()
    }

    @Test
    fun overview_body_test() = runTest {
        movieCache.setSelectedMovieId(752623)
        composeTestRule.setContent {
            TheMovieDatabaseTheme {
                val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Instructions)
                Scaffold { padding ->
                    MovieDetailsScreen(
                        widthSizeClass = WindowWidthSizeClass.Compact,
                        modifier = Modifier.padding(padding),
                        uiState = uiState,
                        onBackButtonClicked = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("MovieOverviewBody").assertIsDisplayed()
    }
}