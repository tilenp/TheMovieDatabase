package com.example.themoviedatabase.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberImagePainter
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.utils.UIText

@Composable
fun MovieItemView(
    modifier: Modifier = Modifier,
    movieId: Long = 0,
    title: UIText = UIText(),
    posterPath: ImagePath = ImagePath(),
    rating: Float = 0f,
    onMovieClick: (Long) -> Unit = {}
) {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    val spacingM = dimensionResource(R.dimen.spacing_m)
    Surface(
        shape = RoundedCornerShape(spacingM),
        elevation = spacingS
    ) {
        Box(
            modifier = modifier.clickable { onMovieClick(movieId) },
        ) {
            MovieImage(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("MovieImage${movieId}"),
                posterPath = posterPath
            )
            RatingView(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = spacingM, end = spacingM)
                    .testTag("RatingView${movieId}"),
                rating = rating.toString(),
                style = MaterialTheme.typography.caption,
                padding = spacingS
            )
            MovieInfo(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x97000000))
                    .align(Alignment.BottomCenter)
                    .padding(spacingM),
                title = title
            )
        }
    }
}

@Composable
private fun MovieImage(
    modifier: Modifier,
    posterPath: ImagePath = ImagePath(),
) {
    val painter =
        rememberImagePainter(data = posterPath.medium) {
            crossfade(durationMillis = 200)
            placeholder(posterPath.placeholder)
            error(posterPath.backup)
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
    movieId: Long = 0,
    title: UIText = UIText(),
) {
    val context = LocalContext.current
    val spacingS = dimensionResource(R.dimen.spacing_s)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacingS),
    ) {
        MovieTitle(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("MovieTitle${movieId}"),
            title = title.asString(context)
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