package com.example.themoviedatabase.ui.movie_details

import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.model.domain.Video
import org.junit.Assert.*
import org.junit.Test

class MovieDetailsStateTest {

    @Test
    fun add_movie_details_test() {
        // arrange
        val movieDetails = MovieDetails(movieId = 1)
        val state = MovieDetailsState.Builder()
            .movieDetails(movieDetails)
            .build()

        // assert
        assertEquals(movieDetails, state.movieDetails)
    }

    @Test
    fun add_movie_details_initial_error_test() {
        // arrange
        val state = MovieDetailsState.Builder()
            .movieDetailsError(Throwable())
            .build()

        // assert
        assertEquals(R.string.An_error_occurred_while_loading_movie_details, state.error!!.message)
        assertEquals(R.string.Retry, state.error!!.actionLabel)
        assertTrue(state.error!!.action.actions.contains(ActionType.LOAD_MOVIE_DETAILS))
        assertTrue(state.error!!.action.actions.size == 1)
    }

    @Test
    fun add_movie_details_incremental_error_test() {
        // arrange
        val state = MovieDetailsState.Builder()
            .videosError(Throwable())
            .movieDetailsError(Throwable())
            .build()

        // assert
        assertEquals(R.string.An_error_occurred_while_loading_movie_details, state.error!!.message)
        assertEquals(R.string.Retry, state.error!!.actionLabel)
        assertTrue(state.error!!.action.actions.contains(ActionType.LOAD_VIDEOS))
        assertTrue(state.error!!.action.actions.contains(ActionType.LOAD_MOVIE_DETAILS))
        assertTrue(state.error!!.action.actions.size == 2)
    }

    @Test
    fun add_videos_test() {
        // arrange
        val videos = listOf(Video(id = "id1"), Video(id = "id2"))
        val state = MovieDetailsState.Builder()
            .videos(videos)
            .build()

        // assert
        assertEquals(videos, state.videos)
    }

    @Test
    fun add_videos_initial_error_test() {
        // arrange
        val state = MovieDetailsState.Builder()
            .videosError(Throwable())
            .build()

        // assert
        assertEquals(R.string.An_error_occurred_while_loading_videos, state.error!!.message)
        assertEquals(R.string.Retry, state.error!!.actionLabel)
        assertTrue(state.error!!.action.actions.contains(ActionType.LOAD_VIDEOS))
        assertTrue(state.error!!.action.actions.size == 1)
    }

    @Test
    fun add_videos_incremental_error_test() {
        // arrange
        val state = MovieDetailsState.Builder()
            .movieDetailsError(Throwable())
            .videosError(Throwable())
            .build()

        // assert
        assertEquals(R.string.An_error_occurred_while_loading_movie_details, state.error!!.message)
        assertEquals(R.string.Retry, state.error!!.actionLabel)
        assertTrue(state.error!!.action.actions.contains(ActionType.LOAD_MOVIE_DETAILS))
        assertTrue(state.error!!.action.actions.contains(ActionType.LOAD_VIDEOS))
        assertTrue(state.error!!.action.actions.size == 2)
    }

    @Test
    fun add_similar_movies_test() {
        // arrange
        val similarMovies = listOf(MovieSummary(movieId = 1), MovieSummary(movieId = 2))
        val state = MovieDetailsState.Builder()
            .similarMovies(similarMovies)
            .build()

        // assert
        assertEquals(similarMovies, state.similarMovies)
    }

    @Test
    fun add_similar_movies_initial_error_test() {
        // arrange
        val state = MovieDetailsState.Builder()
            .similarMoviesError(Throwable())
            .build()

        // assert
        assertEquals(R.string.An_error_occurred_while_loading_similar_movies, state.error!!.message)
        assertEquals(R.string.Retry, state.error!!.actionLabel)
        assertTrue(state.error!!.action.actions.contains(ActionType.LOAD_SIMILAR_MOVIES))
        assertTrue(state.error!!.action.actions.size == 1)
    }

    @Test
    fun add_similar_movies_incremental_error_test() {
        // arrange
        val state = MovieDetailsState.Builder()
            .movieDetailsError(Throwable())
            .similarMoviesError(Throwable())
            .build()

        // assert
        assertEquals(R.string.An_error_occurred_while_loading_movie_details, state.error!!.message)
        assertEquals(R.string.Retry, state.error!!.actionLabel)
        assertTrue(state.error!!.action.actions.contains(ActionType.LOAD_MOVIE_DETAILS))
        assertTrue(state.error!!.action.actions.contains(ActionType.LOAD_SIMILAR_MOVIES))
        assertTrue(state.error!!.action.actions.size == 2)
    }

    @Test
    fun instruction_message_test() {
        // arrange
        val instructionMessage = R.string.Select_a_movie
        val state = MovieDetailsState.Builder()
            .instructionMessage(R.string.Select_a_movie)
            .build()

        // assert
        assertEquals(instructionMessage, state.instructionMessage)
    }

    @Test
    fun reset_instruction_message_test() {
        // arrange
        val state = MovieDetailsState.Builder()
            .instructionMessage(R.string.Select_a_movie)
            .instructionMessage(null)
            .build()

        // assert
        assertNull(state.instructionMessage)
    }

    @Test
    fun reset_error_test() {
        // arrange
        val state = MovieDetailsState.Builder()
            .movieDetailsError(Throwable())
            .resetError()
            .build()

        // assert
        assertNull(state.error)
    }
}