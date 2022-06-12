package com.example.themoviedatabase.database.query

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.themoviedatabase.database.table.BackdropImageTable
import com.example.themoviedatabase.database.table.GenreTable
import com.example.themoviedatabase.database.table.MovieGenreTable
import com.example.themoviedatabase.database.table.MovieTable

data class MovieDetailsQuery (
    @Embedded
    val movieTable: MovieTable = MovieTable(),
    @Relation(
        entity = BackdropImageTable::class,
        parentColumn = "movieId",
        entityColumn = "itemId",
        projection = ["path"]
    )
    val backdropPaths: List<String>? = null,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreTable::class)
    )
    val genres: List<GenreTable>? = null
)