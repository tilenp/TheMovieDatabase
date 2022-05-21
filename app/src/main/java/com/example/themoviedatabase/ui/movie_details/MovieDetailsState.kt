package com.example.themoviedatabase.ui.movie_details

import androidx.compose.runtime.Immutable
import com.example.themoviedatabase.model.domain.MovieDetails

@Immutable
data class MovieDetailsState (
   val movieDetails: MovieDetails = MovieDetails()
) {
    companion object {
        val Empty = MovieDetailsState()
    }
}
