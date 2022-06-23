package com.example.themoviedatabase.service.impl

import android.content.Context
import androidx.paging.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.themoviedatabase.dagger.FakeApplication
import com.example.themoviedatabase.database.MovieDatabase
import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.mapper.table.BackdropImageTableMapper
import com.example.themoviedatabase.mapper.table.ImagePathTableMapper
import com.example.themoviedatabase.mapper.table.MovieTableMapper
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
import com.example.themoviedatabase.network.FakeMovieApi
import com.example.themoviedatabase.network.FakeResponse
import com.example.themoviedatabase.network.MovieRequestQuery
import com.example.themoviedatabase.utils.FileReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.reflect.Type
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class MovieRemoteMediatorTest {

    @Inject
    lateinit var fakeMovieApi: FakeMovieApi

    @Inject
    lateinit var database: MovieDatabase

    @Inject
    lateinit var gson: Gson

    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var remoteMediator: MovieRemoteMediator
    private lateinit var pagingState: PagingState<Int, MovieSummaryQuery>

    @Before
    fun setUp() {
        (context as FakeApplication).provideAppComponent().inject(this)
        remoteMediator = MovieRemoteMediator(
            movieApi = fakeMovieApi,
            database = database,
            queryBuilder = MovieRequestQuery.Builder(),
            movieTableMapper = MovieTableMapper(),
            backdropImageTableMapper = BackdropImageTableMapper(),
            imagePathTableMapper = ImagePathTableMapper()
        )
        pagingState = PagingState(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )
    }

    @After
    fun tearDown() {
        database.clearAllTables()
    }

    @Test
    fun refresh_load_success() = runTest {
        val json = FileReader.readFile(context, "movies_popular_desc_page_1_200")
        val bodyType: Type = object : TypeToken<PagingDTO<MovieDTO>>() {}.type
        val body: PagingDTO<MovieDTO> = gson.fromJson(json, bodyType)
        val fakeResponse = FakeResponse(code = 200, body = body)
        fakeMovieApi.enqueueMovieSummaries(fakeResponse)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refresh_load_success_end_reached() = runTest {
        val json = FileReader.readFile(context, "movies_popular_desc_end_reached_200")
        val bodyType: Type = object : TypeToken<PagingDTO<MovieDTO>>() {}.type
        val body: PagingDTO<MovieDTO> = gson.fromJson(json, bodyType)
        val fakeResponse = FakeResponse(code = 200, body = body)
        fakeMovieApi.enqueueMovieSummaries(fakeResponse)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refresh_load_error() = runTest {
        val errorMessage = "network not available"
        val ioException = IOException(errorMessage)
        val testResponse = FakeResponse<PagingDTO<MovieDTO>>(code = 400, throwable = ioException)
        fakeMovieApi.enqueueMovieSummaries(testResponse)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}