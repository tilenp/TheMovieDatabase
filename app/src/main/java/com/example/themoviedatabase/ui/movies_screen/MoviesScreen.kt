package com.example.themoviedatabase.ui.movies_screen

import ComposablePagedGrid
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.ui.common.ErrorView
import com.example.themoviedatabase.ui.common.MovieItemView
import com.example.themoviedatabase.ui.common.MyButton

@Composable
fun MovieListContent(
    columns: Int,
    modifier: Modifier = Modifier,
    pagesMovies: LazyPagingItems<MovieSummary>,
    onMovieClick: (Long) -> Unit = {},
    getErrorMessageId: (Throwable) -> Int
) {
    val imageSize = dimensionResource(R.dimen.image_size)
    val spacingL = dimensionResource(R.dimen.spacing_l)
    ComposablePagedGrid(
        columns = columns,
        modifier = modifier,
        pagedItems = pagesMovies,
        itemContent = { movie ->
            MovieItemView(
                modifier = Modifier
                    .height(imageSize)
                    .testTag("MovieItem"),
                movieId = movie.movieId,
                title = movie.title,
                posterPath = movie.posterPath,
                rating = movie.rating,
                onMovieClick = onMovieClick
            )
        },
        errorContent = { modifier, throwable, retry ->
            ErrorView(
                modifier = modifier
                    .padding(spacingL)
                    .testTag("MoviesScreenErrorView"),
                message = stringResource(getErrorMessageId(throwable)),
                buttonsContent = {
                    MyButton(
                        title = stringResource(R.string.Click_to_retry),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                        onClick = { retry() }
                    )
                }
            )
        }
    )
}