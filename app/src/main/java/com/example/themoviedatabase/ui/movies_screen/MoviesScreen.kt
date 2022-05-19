package com.example.themoviedatabase.ui.movies_screen

import ComposablePagedList
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
fun MoviesScreen(viewModel: MoviesViewModel) {

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
        topBar = { MyTopBar() },
        content = { padding ->
            MovieListContent(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                pagesMovies = pagedMovies,
                getErrorMessageId = { viewModel.getErrorMessage(it) }
            )
        }
    )
}

@Composable
fun MovieListContent(
    modifier: Modifier,
    pagesMovies: LazyPagingItems<MovieSummary>,
    getErrorMessageId: (Throwable) -> Int
) {
    ComposablePagedList(
        modifier = modifier,
        pagedItems = pagesMovies,
        itemContent = { movie ->
            MovieItem(
                modifier = Modifier.height(300.dp),
                movie = movie
            )
        },
        errorContent = { modifier, throwable, retry ->
            ErrorView(
                modifier = modifier.padding(12.dp),
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
fun MovieItem(modifier: Modifier, movie: MovieSummary, onMovieClicked: (Int) -> Unit = {}) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = modifier,
        ) {
            MovieImage(
                modifier = Modifier.fillMaxSize(),
                movie = movie
            )
            MovieRating(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp),
                movie = movie
            )
            MovieInfo(
                movie,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color(0x97000000))
            )
        }
    }
}

@Composable
private fun MovieImage(modifier: Modifier, movie: MovieSummary) {
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
private fun MovieRating(modifier: Modifier, movie: MovieSummary) {
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
private fun MovieInfo(movie: MovieSummary, modifier: Modifier) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(8.dp)
    ) {
        MovieName(name = movie.title.asString(context))
    }
}

@Composable
private fun MovieName(name: String) = Text(
    text = name,
    style = MaterialTheme.typography.caption.copy(color = Color.White),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis
)
