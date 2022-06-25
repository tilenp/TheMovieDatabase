package com.example.themoviedatabase.ui.movie_details

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.ui.common.MySnackbar
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import com.example.themoviedatabase.utils.UIPlural
import com.example.themoviedatabase.utils.UIText
import com.example.themoviedatabase.utils.UIValue
import com.example.themoviedatabase.utils.thousandFormat

@Composable
fun MovieDetailsScreen(
    widthSizeClass: WindowWidthSizeClass,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    uiState: MovieDetailsState,
    onBackButtonClicked: () -> Unit,
    onVideoClick: (String) -> Unit,
    onSnackbarActionPerformed: (Event.Load) -> Unit
) {
    if (uiState.instructionMessage != null) {
        ShowInstructions(
            modifier = modifier
                .fillMaxSize(),
            messageId = uiState.instructionMessage
        )
    } else {
        ShowContent(
            widthSizeClass = widthSizeClass,
            modifier = modifier
                .fillMaxSize(),
            uiState = uiState,
            onBackButtonClicked = onBackButtonClicked,
            onVideoClick = onVideoClick
        )
    }
    if (uiState.error != null) {
        MySnackbar(
            scaffoldState = scaffoldState,
            content = uiState.error,
            onSnackbarActionPerformed = onSnackbarActionPerformed
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
        MovieDetailsView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag("MovieDetailsView"),
            widthSizeClass = widthSizeClass,
            movieDetails = uiState.movieDetails,
            onBackButtonClicked = onBackButtonClicked
        )
        MovieTrailersView(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("MovieTrailersView"),
            videos = uiState.videos,
            onVideoClick = onVideoClick
        )
        SimilarMoviesView(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("SimilarMoviesView"),
            movies = uiState.similarMovies,
            onMovieClick = {}
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
            modifier = Modifier
                .fillMaxSize()
                .testTag("ShowInstructions"),
            text = stringResource(id = messageId),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun PreviewMovieDetailsScreen() {
    TheMovieDatabaseTheme {
        TheMovieDatabaseTheme {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState
            ) { padding ->
                MovieDetailsScreen(
                    widthSizeClass = WindowWidthSizeClass.Compact,
                    scaffoldState = scaffoldState,
                    modifier = Modifier.padding(padding),
                    uiState = previewMovieDetailsState(),
                    onBackButtonClicked = {},
                    onVideoClick = {},
                    onSnackbarActionPerformed = {}
                )
            }
        }
    }
}

private fun previewMovieDetailsState(): MovieDetailsState {
    return MovieDetailsState.Builder()
        .movieDetails(previewMovieDetails())
        .videos(listOf(Video(), Video(), Video(), Video()))
        .similarMovies(previewSimilarMovies())
        .build()
}

private fun previewMovieDetails(): MovieDetails {
    return MovieDetails(
        movieId = 752623,
        title = UIText("The Lost City"),
        backdropPath = ImagePath(
            url = "/neMZH82Stu91d3iqvLdNQfqPPyl.jpg",
            placeholder = R.drawable.ic_photo,
            resourceId = R.drawable.ic_broken_image
        ),
        rating = UIValue(
            value = 6.7f,
            formattedValue = "6.7"
        ),
        ratingCount = UIPlural(
            pluralId = R.plurals.rating_count_format,
            formatArgs = 1276L.thousandFormat(),
            count = 1276L
        ),
        genres = "Action, Comedy",
        overview = UIText("A reclusive romance novelist..."),
        releaseDate = "release date",
        runtimeIcon = ImagePath(resourceId = R.drawable.clock),
        runtime = "runtime"
    )
}

private fun previewSimilarMovies(): List<MovieSummary> {
    return listOf(
        MovieSummary(
            movieId = 752623,
            title = UIText("The Lost City"),
            posterPath = ImagePath(
                url = "/neMZH82Stu91d3iqvLdNQfqPPyl.jpg",
                placeholder = R.drawable.ic_photo,
                resourceId = R.drawable.ic_broken_image
            ),
            rating = UIValue(
                value = 6.7f,
                formattedValue = "6.7"
            )
        ),
        MovieSummary(
            movieId = 675353,
            title = UIText("Sonic the Hedgehog 2"),
            posterPath = ImagePath(
                url = "/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
                placeholder = R.drawable.ic_photo,
                resourceId = R.drawable.ic_broken_image
            ),
            rating = UIValue(
                value = 7.7f,
                formattedValue = "7.7"
            )
        ),
        MovieSummary(
            movieId = 752623,
            title = UIText("Uncharted"),
            posterPath = ImagePath(
                url = "/tlZpSxYuBRoVJBOpUrPdQe9FmFq.jpg",
                placeholder = R.drawable.ic_photo,
                resourceId = R.drawable.ic_broken_image
            ),
            rating = UIValue(
                value = 7.2f,
                formattedValue = "7.2"
            )
        )
    )
}