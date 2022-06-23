package com.example.themoviedatabase.ui.movie_details

import org.junit.Assert.assertTrue
import org.junit.Test

class EventTest {

    @Test
    fun add_action_type_test() {
        // arrange
        val event = Event.Load.Builder()
            .add(ActionType.LOAD_MOVIE_DETAILS)
            .add(ActionType.LOAD_VIDEOS)
            .add(ActionType.LOAD_SIMILAR_MOVIES)
            .build()

        // assert
        assertTrue(event.actions.contains(ActionType.LOAD_MOVIE_DETAILS))
        assertTrue(event.actions.contains(ActionType.LOAD_VIDEOS))
        assertTrue(event.actions.contains(ActionType.LOAD_SIMILAR_MOVIES))
    }

    @Test
    fun filter_duplicated_actions_test() {
        // arrange
        val event = Event.Load.Builder()
            .add(ActionType.LOAD_MOVIE_DETAILS)
            .add(ActionType.LOAD_MOVIE_DETAILS)
            .build()

        // assert
        assertTrue(event.actions.contains(ActionType.LOAD_MOVIE_DETAILS))
        assertTrue(event.actions.size == 1)
    }

    @Test
    fun builder_constructor_test() {
        // arrange
        val event = Event.Load.Builder()
            .add(ActionType.LOAD_MOVIE_DETAILS)
            .build()
        val updatedEvent = Event.Load.Builder(event)
            .add(ActionType.LOAD_VIDEOS)
            .build()

        // assert
        assertTrue(updatedEvent.actions.contains(ActionType.LOAD_MOVIE_DETAILS))
        assertTrue(updatedEvent.actions.contains(ActionType.LOAD_VIDEOS))
        assertTrue(updatedEvent.actions.size == 2)
    }
}