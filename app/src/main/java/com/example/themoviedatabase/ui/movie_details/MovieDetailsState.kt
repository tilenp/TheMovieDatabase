package com.example.themoviedatabase.ui.movie_details

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.MovieDetails

@Immutable
data class MovieDetailsState (
   val movieDetails: MovieDetails? = null,
    @StringRes val instructionMessage: Int
) {
    companion object {
        val Empty = MovieDetailsState(instructionMessage = R.string.Select_a_movie)
    }
}
