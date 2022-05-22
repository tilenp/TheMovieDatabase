package com.example.themoviedatabase.ui.movies_screen

import ComposablePagedGrid
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberImagePainter
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.ui.common.ErrorView
import com.example.themoviedatabase.ui.common.MyButton
import com.example.themoviedatabase.ui.common.RatingView

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
            MovieItem(
                modifier = Modifier
                    .height(imageSize)
                    .testTag("MovieItem"),
                movie = movie,
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

@Composable
private fun MovieItem(
    modifier: Modifier,
    movie: MovieSummary,
    onMovieClick: (Long) -> Unit = {}
) {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    val spacingM = dimensionResource(R.dimen.spacing_m)
    Surface(
        shape = RoundedCornerShape(spacingM),
        elevation = spacingS
    ) {
        Box(
            modifier = modifier.clickable { onMovieClick(movie.movieId) },
        ) {
            MovieImage(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("MovieImage${movie.movieId}"),
                movie = movie
            )
            RatingView(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = spacingM, end = spacingM)
                    .testTag("RatingView${movie.movieId}"),
                rating = movie.rating.toString(),
                style = MaterialTheme.typography.caption,
                padding = spacingS
            )
            MovieInfo(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color(0x97000000)),
                movie = movie
            )
        }
    }
}

@Composable
private fun MovieImage(
    modifier: Modifier,
    movie: MovieSummary
) {
    val painter =
        rememberImagePainter(data = movie.posterPath.medium) {
            crossfade(durationMillis = 200)
            placeholder(movie.posterPath.placeholder)
            error(movie.posterPath.backup)
        }
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = "Movie Image",
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun MovieInfo(
    modifier: Modifier,
    movie: MovieSummary
) {
    val context = LocalContext.current
    val spacingS = dimensionResource(R.dimen.spacing_s)
    val spacingM = dimensionResource(R.dimen.spacing_m)
    Column(
        verticalArrangement = Arrangement.spacedBy(spacingS),
        modifier = modifier.padding(spacingM)
    ) {
        MovieTitle(
            modifier = Modifier
                .testTag("MovieTitle${movie.movieId}"),
            title = movie.title.asString(context)
        )
    }
}

@Composable
private fun MovieTitle(
    modifier: Modifier,
    title: String
) = Text(
    modifier = modifier,
    text = title,
    color = Color.White,
    style = MaterialTheme.typography.caption,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis
)
