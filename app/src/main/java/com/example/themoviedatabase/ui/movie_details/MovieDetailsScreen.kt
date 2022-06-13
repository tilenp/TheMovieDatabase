package com.example.themoviedatabase.ui.movie_details

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberImagePainter
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.ui.common.*

@Composable
fun MovieDetailsScreen(
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    uiState: MovieDetailsState,
    onBackButtonClicked: () -> Unit,
    onVideoClick: (String) -> Unit
) {
    if (uiState.instructionMessage != null) {
        ShowInstructions(
            modifier = modifier,
            messageId = uiState.instructionMessage
        )
    } else {
        ShowContent(
            widthSizeClass = widthSizeClass,
            modifier = modifier,
            uiState = uiState,
            onBackButtonClicked = onBackButtonClicked,
            onVideoClick = onVideoClick
        )
    }
}

@Composable
private fun ShowInstructions(
    modifier: Modifier = Modifier,
    @StringRes messageId: Int
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxSize()
                .testTag("ShowInstructions"),
            text = stringResource(id = messageId),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ShowContent(
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    uiState: MovieDetailsState,
    onBackButtonClicked: () -> Unit,
    onVideoClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        ShowMovieDetails(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f),
            widthSizeClass = widthSizeClass,
            movieDetails = uiState.movieDetails,
            onBackButtonClicked = onBackButtonClicked
        )
        ShowTrailers(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            videos = uiState.videos,
            onVideoClick = onVideoClick
        )
    }
}

@Composable
private fun ShowMovieDetails(
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails?,
    onBackButtonClicked: () -> Unit
) {
    if (movieDetails == null) {
        LoadingView(
            modifier = modifier
        )
    } else {
        val imageSize = dimensionResource(R.dimen.image_size)
        Backdrop(
            showBackButton = widthSizeClass != WindowWidthSizeClass.Expanded,
            modifier = Modifier
                .height(imageSize),
            imagePath = movieDetails.backdropPath,
            onBackButtonClicked = onBackButtonClicked
        )
        MovieInfo(movie = movieDetails)
    }
}

@Composable
private fun Backdrop(
    modifier: Modifier = Modifier,
    imagePath: ImagePath,
    onBackButtonClicked: () -> Unit,
    showBackButton: Boolean
) {
    val spacingM = dimensionResource(R.dimen.spacing_m)
    val spacingL = dimensionResource(R.dimen.spacing_l)
    val iconSize = dimensionResource(R.dimen.icon_size)
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
        if (showBackButton) {
            IconButton(
                onClick = onBackButtonClicked,
                modifier = Modifier
                    .padding(start = spacingM, top = spacingM)
                    .testTag("BackdropIconButton"),
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(iconSize)
                        .padding(spacingL),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun MovieInfo(
    modifier: Modifier = Modifier,
    movie: MovieDetails
) {
    val context = LocalContext.current
    val spacingM = dimensionResource(R.dimen.spacing_m)
    val spacingXL = dimensionResource(R.dimen.spacing_xl)
    Column(
        modifier = modifier
            .padding(spacingXL),
        verticalArrangement = Arrangement.spacedBy(spacingM)
    ) {
        MovieTitle(
            title = movie.title.asString(context)
        )
        MovieRatingView(
            rating = movie.rating.toString(),
            ratingCount = movie.ratingCount.asString(context)
        )
        MovieGenres(
            genres = movie.genres
        )
        MovieOverviewTitle()
        MovieOverviewBody(
            overview = movie.overview.asString(context)
        )
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
    val spacingS = dimensionResource(R.dimen.spacing_s)
    val spacingM = dimensionResource(R.dimen.spacing_m)
    val spacingL = dimensionResource(R.dimen.spacing_l)
    Row(
        modifier = modifier
            .padding(top = spacingL)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacingM)
    ) {
        RatingView(
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .testTag("RatingView${rating}"),
            rating = rating,
            style = MaterialTheme.typography.body2,
            padding = spacingS
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
private fun MovieGenres(
    modifier: Modifier = Modifier,
    genres: String?
) {
    if (genres != null) {
        Text(
            modifier = modifier
                .testTag("MovieGenres"),
            text = genres,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.caption
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

@Composable
private fun ShowTrailers(
    modifier: Modifier = Modifier,
    videos: List<Video>?,
    onVideoClick: (String) -> Unit
) {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    val trailerSize = dimensionResource(R.dimen.carousel_image_size)
    if (videos == null) {
        LoadingView(
            modifier = modifier
        )
    } else {
        ComposableCarousel(
            title = stringResource(R.string.Trailers),
            paddingValues = PaddingValues(start = spacingS, end = spacingS),
            horizontalArrangement = Arrangement.spacedBy(spacingS),
            list = videos,
            itemContent = { video ->
                VideoItemView(
                    modifier = Modifier
                        .width(trailerSize)
                        .aspectRatio(ratio = 16f.div(9)),
                    video = video,
                    onVideoClick = onVideoClick
                )
            }
        )
    }
}