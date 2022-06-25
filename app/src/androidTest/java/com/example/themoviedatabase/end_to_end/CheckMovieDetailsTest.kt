package com.example.themoviedatabase.end_to_end

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.MainActivity
import com.example.themoviedatabase.R
import com.example.themoviedatabase.dagger.FakeApplication
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
import com.example.themoviedatabase.model.dto.ResponseDTO
import com.example.themoviedatabase.model.dto.VideoDTO
import com.example.themoviedatabase.network.FakeMovieApi
import com.example.themoviedatabase.network.FakeResponse
import com.example.themoviedatabase.utils.DispatcherProvider
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
class CheckMovieDetailsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var fakeMovieApi: FakeMovieApi

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider
    lateinit var fakeDispatcherProvider: FakeDispatcherProvider

    @Inject
    lateinit var uriIdlingResource: UriIdlingResource

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        (context as FakeApplication).provideAppComponent().inject(this)
        fakeDispatcherProvider = dispatcherProvider as FakeDispatcherProvider
        IdlingRegistry.getInstance().register(uriIdlingResource)

        // movies screen
        enqueueMovieSummaries(code = 200, fileName = "paging_2_movies_page_1_200", false)
        enqueueMovieSummaries(code = 200, fileName = "paging_2_movies_page_2_200", false)

        // movie details screen
        enqueueMovieDetails(code = 200, fileName = "movie_details_200", false)
        enqueueVideos(code = 200, fileName = "movie_videos_200", false)

        // similar movies - network not available response
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 400, throwable = IOException())
        fakeMovieApi.enqueueSimilarMovies(testResponse)

        // similar movies retry response
        enqueueSimilarMovies(code = 200, fileName = "similar_movies_200", false)
    }

    @After
    fun cleanUp() {
        IdlingRegistry.getInstance().unregister(uriIdlingResource)
    }

    private fun enqueueMovieSummaries(code: Int, fileName: String, testLoading: Boolean) {
        val json = FileReader.readFile(context, fileName)
        val bodyType: Type = object : TypeToken<PagingDTO<MovieDTO>>() {}.type
        val body: PagingDTO<MovieDTO> = gson.fromJson(json, bodyType)
        fakeMovieApi.enqueueMovieSummaries(FakeResponse(code, body, testLoading))
    }

    private fun enqueueMovieDetails(code: Int, fileName: String, testLoading: Boolean) {
        val json = FileReader.readFile(context, fileName)
        val bodyType: Type = object : TypeToken<MovieDTO>() {}.type
        val body: MovieDTO = gson.fromJson(json, bodyType)
        fakeMovieApi.enqueueMovieDetails(FakeResponse(code, body, testLoading))
    }

    private fun enqueueVideos(code: Int, fileName: String, testLoading: Boolean) {
        val json = FileReader.readFile(context, fileName)
        val bodyType: Type = object : TypeToken<ResponseDTO<VideoDTO>>() {}.type
        val body: ResponseDTO<VideoDTO> = gson.fromJson(json, bodyType)
        fakeMovieApi.enqueueVideos(FakeResponse(code, body, testLoading))
    }

    private fun enqueueSimilarMovies(code: Int, fileName: String, testLoading: Boolean) {
        val json = FileReader.readFile(context, fileName)
        val bodyType: Type = object : TypeToken<PagingDTO<MovieDTO>>() {}.type
        val body: PagingDTO<MovieDTO> = gson.fromJson(json, bodyType)
        fakeMovieApi.enqueueSimilarMovies(FakeResponse(code, body, testLoading))
    }

    @Test
    fun test() {
        ActivityScenario.launch(MainActivity::class.java)

        /** MoviesScreen - select movie **/
        composeTestRule
            .onNode(
                hasTestTag("MovieImage752623") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).performClick()

        /** MovieDetailsScreen - movie details are displayed **/
        composeTestRule.onNodeWithTag("MovieTitleThe Lost City").assertIsDisplayed()

        /** MovieDetailsScreen - trailers are displayed **/
        composeTestRule.onNodeWithTag("CarouselViewTrailers").assertIsDisplayed()

        /** MovieDetailsScreen - similar movies response 400, similar movies don't exist **/
        composeTestRule.onNodeWithTag("CarouselViewSimilar movies").assertDoesNotExist()

        /** MovieDetailsScreen - reload similar movies **/
        composeTestRule.onNodeWithText(context.getString(R.string.Retry)).performClick()

        /** MovieDetailsScreen - similar movies response 200, similar movies exist **/
        composeTestRule.onNodeWithTag("CarouselViewSimilar movies").assertExists()

        /** MovieDetailsScreen - back button click **/
        composeTestRule.onNodeWithTag("BackdropIconButton").performClick()
    }
}