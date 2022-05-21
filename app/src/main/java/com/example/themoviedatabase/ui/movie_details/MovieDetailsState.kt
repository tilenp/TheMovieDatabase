package com.example.themoviedatabase.ui.movie_details

import androidx.compose.runtime.Immutable
import com.example.themoviedatabase.model.domain.MovieSummary

@Immutable
data class MovieDetailsState (
   val movieSummary: MovieSummary = MovieSummary()
) {
    companion object {
        val Empty = MovieDetailsState()
    }
}
