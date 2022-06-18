package com.example.themoviedatabase.ui.movie_details

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.themoviedatabase.ui.common.MySnackbar

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
    if (uiState.instructionMessage == null) {
        ShowContent(
            widthSizeClass = widthSizeClass,
            modifier = modifier,
            uiState = uiState,
            onBackButtonClicked = onBackButtonClicked,
            onVideoClick = onVideoClick
        )
    } else {
        ShowInstructions(
            modifier = modifier,
            messageId = uiState.instructionMessage
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
                .weight(4f),
            widthSizeClass = widthSizeClass,
            movieDetails = uiState.movieDetails,
            onBackButtonClicked = onBackButtonClicked
        )
        MovieTrailersView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            videos = uiState.videos,
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
        modifier = modifier.fillMaxSize(),
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