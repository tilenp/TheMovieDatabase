package com.example.themoviedatabase.ui.movie_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.ui.common.RatingView

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Empty)
    Scaffold(
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            BackdropImage(
                modifier = Modifier.height(300.dp),
                movie = uiState.movieDetails
            )
            MovieTitle(
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
                    .testTag("MovieTitle${uiState.movieDetails.movieId}"),
                title = uiState.movieDetails.title.asString(context)
            )
            MovieOverview(
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                overview = uiState.movieDetails.overview.asString(context)
            )
        }
    }
}

@Composable
private fun BackdropImage(
    modifier: Modifier = Modifier,
    movie: MovieDetails
) {
    val painter = rememberImagePainter(data = movie.backdropPath.medium) {
        crossfade(durationMillis = 200)
        placeholder(movie.backdropPath.placeholder)
        error(movie.backdropPath.backup)
    }
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .testTag("BackdropImage${movie.movieId}"),
            painter = painter,
            contentDescription = "backdrop Image",
            contentScale = ContentScale.Crop
        )
        RatingView(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp, end = 8.dp)
                .testTag("RatingView${movie.movieId}"),
            rating = movie.rating.toString(),
            style = MaterialTheme.typography.body2,
            padding = 5.dp
        )
    }
}

@Composable
private fun MovieTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier,
        text = title,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h6,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun MovieInfo(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails
) {
    val context = LocalContext.current
    val painter = rememberImagePainter(data = movieDetails.posterPath.medium) {
        crossfade(durationMillis = 200)
        placeholder(movieDetails.posterPath.placeholder)
        error(movieDetails.posterPath.backup)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Image(
            modifier = Modifier
                .height(250.dp)
                .testTag("MoviePoster${movieDetails.movieId}"),
            painter = painter,
            contentDescription = "poster Image",
            contentScale = ContentScale.FillHeight,
        )
        MovieOverview(
            modifier = Modifier
                .weight(1f)
                .testTag("MovieOverview${movieDetails.movieId}"),
            overview = movieDetails.overview.asString(context)
        )
    }
}

@Composable
private fun MovieOverview(
    modifier: Modifier,
    overview: String
) {
    Text(
        modifier = modifier,
        text = overview,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body1,
        maxLines = 15,
        overflow = TextOverflow.Ellipsis
    )
}