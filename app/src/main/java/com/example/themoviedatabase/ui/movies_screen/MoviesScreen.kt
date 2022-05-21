package com.example.themoviedatabase.ui.movies_screen

import ComposablePagedList
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.ui.common.ErrorView
import com.example.themoviedatabase.ui.common.MyButton
import com.example.themoviedatabase.ui.common.MyTopBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel,
    onMovieClick: (Long) -> Unit = {}
) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.surface
    val pagedMovies = viewModel.movies.collectAsLazyPagingItems()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
        systemUiController.setNavigationBarColor(
            color = systemBarColor
        )
    }

    Scaffold(
        topBar = {
            MyTopBar(
                title = stringResource(R.string.app_name)
            )
        },
        content = { padding ->
            MovieListContent(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                pagesMovies = pagedMovies,
                onMovieClick = onMovieClick,
                getErrorMessageId = { viewModel.getErrorMessage(it) }
            )
        }
    )
}

@Composable
fun MovieListContent(
    modifier: Modifier,
    pagesMovies: LazyPagingItems<MovieSummary>,
    onMovieClick: (Long) -> Unit = {},
    getErrorMessageId: (Throwable) -> Int
) {
    ComposablePagedList(
        modifier = modifier,
        pagedItems = pagesMovies,
        itemContent = { movie ->
            MovieItem(
                modifier = Modifier.height(300.dp),
                movie = movie,
                onMovieClick = onMovieClick
            )
        },
        errorContent = { modifier, throwable, retry ->
            ErrorView(
                modifier = modifier
                    .padding(12.dp)
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
fun MovieItem(
    modifier: Modifier,
    movie: MovieSummary,
    onMovieClick: (Long) -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
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
            MovieRating(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp)
                    .testTag("MovieRating${movie.movieId}"),
                movie = movie
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
private fun MovieRating(
    modifier: Modifier,
    movie: MovieSummary
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = MaterialTheme.colors.secondary,
                shape = CircleShape
            )
            .layout { measurable, constraints ->
                // Measure the composable
                val placeable = measurable.measure(constraints)

                //get the current max dimension to assign width=height
                val currentHeight = placeable.height
                var heightCircle = currentHeight
                if (placeable.width > heightCircle)
                    heightCircle = placeable.width

                //assign the dimension and the center position
                layout(heightCircle, heightCircle) {
                    // Where the composable gets placed
                    placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                }
            },
    ) {
        Text(
            text = movie.rating.toString(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.background,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
private fun MovieInfo(
    modifier: Modifier,
    movie: MovieSummary
) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(8.dp)
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
    style = MaterialTheme.typography.caption.copy(color = Color.White),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis
)
