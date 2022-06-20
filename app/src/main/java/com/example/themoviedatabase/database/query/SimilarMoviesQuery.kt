package com.example.themoviedatabase.database.query

import androidx.room.DatabaseView
import androidx.room.Junction
import androidx.room.Relation
import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.database.table.SimilarMovieTable

data class SimilarMoviesQuery (
    val movieId: Long = 0L,
    @Relation(
        entity = MovieTable::class,
        parentColumn = "movieId",
        entityColumn = "movieId",
        associateBy = Junction(SimilarMovieTable::class)
    )
    val similarMovies: List<MovieSummaryQueryTest>
)

data class MovieSummaryQueryTest(
    val movieId: Long = 0L,
    val title: String = "",
    val rating: Float = 0f,
    val imagePath: String? = null
)

@DatabaseView("""
    SELECT 
        MovieTable.movieId, 
        MovieTable.title, 
        MovieTable.rating,
        ImagePathTable.path as imagePath
    FROM MovieTable
    JOIN ImagePathTable ON MovieTable.movieId = ImagePathTable.itemId 
""")
data class MSummary(
    val movieId: Long = 0L,
    val title: String = "",
    val rating: Float = 0f,
    val imagePath: String? = null
)