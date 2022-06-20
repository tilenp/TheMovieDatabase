package com.example.themoviedatabase.ui.movie_details

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.ui.common.CarouselView
import com.example.themoviedatabase.ui.common.MovieItemView

@Composable
fun SimilarMoviesView(
    modifier: Modifier = Modifier,
    movies: List<MovieSummary>?,
    onMovieClick: (Long) -> Unit = {}
) {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    val spacingXL = dimensionResource(R.dimen.spacing_xl)
    val imageSize = dimensionResource(R.dimen.carousel_image_size)
    CarouselView(
        modifier = modifier
            .padding(top = spacingXL),
        title = stringResource(R.string.Similar_movies),
        paddingValues = PaddingValues(start = spacingS, end = spacingS),
        horizontalArrangement = Arrangement.spacedBy(spacingS),
        list = movies,
        itemView = { movie ->
            System.out.println("TTT " + movie.posterPath.medium)
            MovieItemView(
                modifier = Modifier
                    .size(imageSize)
                    .testTag("MovieItem"),
                movieId = movie.movieId,
                title = movie.title,
                posterPath = movie.posterPath,
                rating = movie.rating,
                onMovieClick = onMovieClick
            )
        }
    )
}