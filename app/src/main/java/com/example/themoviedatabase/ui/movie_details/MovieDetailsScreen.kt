package com.example.themoviedatabase.ui.movie_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.ui.common.RatingView

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel,
    onBackButtonClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Empty)
    Scaffold(
        modifier = modifier
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Backdrop(
                modifier = Modifier
                    .height(300.dp),
                imagePath = uiState.movieDetails.backdropPath,
                onBackButtonClicked = onBackButtonClicked
            )
            MovieInfo(movie = uiState.movieDetails)
        }
    }
}

@Composable
private fun MovieInfo(
    modifier: Modifier = Modifier,
    movie: MovieDetails
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MovieTitle(title = movie.title.asString(context))
        MovieRatingView(
            rating = movie.rating.toString(),
            ratingCount = movie.ratingCount.asString(context)
        )
        MovieOverviewTitle()
        MovieOverviewBody(overview = movie.overview.asString(context))
    }
}

@Composable
private fun Backdrop(
    modifier: Modifier = Modifier,
    imagePath: ImagePath,
    onBackButtonClicked: () -> Unit = {}
) {
    val painter = rememberImagePainter(data = imagePath.medium) {
        crossfade(durationMillis = 200)
        placeholder(imagePath.placeholder)
        error(imagePath.backup)
    }
    Box(
        modifier
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .testTag("BackdropImage"),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = onBackButtonClicked,
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .testTag("BackdropIconButton"),
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
private fun MovieTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier
            .testTag("MovieTitle${title}"),
        text = title,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h5,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun MovieRatingView(
    modifier: Modifier = Modifier,
    rating: String,
    ratingCount: String
) {
    Row(
        modifier = modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RatingView(
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .testTag("RatingView${rating}"),
            rating = rating,
            style = MaterialTheme.typography.body2,
            padding = 5.dp
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .testTag("RatingCount${ratingCount}"),
            text = ratingCount,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
private fun MovieOverviewTitle(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .testTag("MovieOverviewTitle"),
        text = stringResource(R.string.Overview),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h6,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun MovieOverviewBody(
    modifier: Modifier = Modifier,
    overview: String
) {
    Text(
        modifier = modifier
            .testTag("MovieOverviewBody"),
        text = overview,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body2,
        maxLines = 15,
        overflow = TextOverflow.Ellipsis
    )
}