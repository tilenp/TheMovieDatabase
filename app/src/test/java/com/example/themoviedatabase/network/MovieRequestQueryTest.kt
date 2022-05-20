package com.example.themoviedatabase.network

import org.junit.Assert.assertEquals
import org.junit.Test

class MovieRequestQueryTest {

    @Test
    fun page_test() {
        // arrange
        val page = 1
        val query = MovieRequestQuery.Builder()
            .page(page)
            .build()

        // assert
        assertEquals(page, query.page)
    }

    @Test
    fun default_sortBy_test() {
        // arrange
        val query = MovieRequestQuery.Builder()
            .build()

        // assert
        assertEquals(SortBy.POPULARITY_DESC.value, query.sortBy)
    }

    @Test
    fun sortBy_test() {
        // arrange
        val query = MovieRequestQuery.Builder()
            .sortBy(SortBy.POPULARITY_DESC)
            .sortBy(SortBy.POPULARITY_DESC)
            .build()

        // assert
        val commaSeparatedString = "${SortBy.POPULARITY_DESC.value},${SortBy.POPULARITY_DESC.value}"
        assertEquals(commaSeparatedString, query.sortBy)
    }
}