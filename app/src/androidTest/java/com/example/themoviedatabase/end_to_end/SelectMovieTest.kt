package com.example.themoviedatabase.end_to_end

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.MainActivity
import com.example.themoviedatabase.dagger.FakeApplication
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
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
import java.lang.reflect.Type
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class SelectMovieTest {

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
        enqueueResponse(code = 200, fileName = "movies_popular_desc_page_1_200", false)
        enqueueResponse(code = 200, fileName = "movies_popular_desc_end_reached_200", false)
    }

    private fun enqueueResponse(code: Int, fileName: String, testLoading: Boolean) {
        val json = FileReader.readFile(context, fileName)
        val bodyType: Type = object : TypeToken<PagingDTO<MovieDTO>>() {}.type
        val body: PagingDTO<MovieDTO> = gson.fromJson(json, bodyType)
        fakeMovieApi.enqueue(FakeResponse(code, body, testLoading))
    }

    @After
    fun cleanUp() {
        IdlingRegistry.getInstance().unregister(uriIdlingResource)
    }

    @Test
    fun test() {
        ActivityScenario.launch(MainActivity::class.java)

        // MoviesScreen - movie is displayed
        composeTestRule
            .onNode(
                hasTestTag("MovieImage752623") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).assertExists()

        // MoviesScreen - select movie
        composeTestRule
            .onNode(
                hasTestTag("MovieImage752623") and
                    hasParent(
                        hasTestTag("MovieItem")
                    ),
                useUnmergedTree = true
            ).performClick()

        // MovieDetailsScreen - selected movie title is shown
        composeTestRule.onNodeWithTag("MovieTitleThe Lost City").assertIsDisplayed()

        // MovieDetailsScreen - back button click
        composeTestRule.onNodeWithTag("BackdropIconButton").performClick()
    }
}