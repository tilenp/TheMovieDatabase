package com.example.themoviedatabase.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.themoviedatabase.R
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    val spacingXL = dimensionResource(R.dimen.spacing_xl)
    CircularProgressIndicator(
        modifier = modifier
            .wrapContentSize(Alignment.Center)
            .padding(spacingXL)
            .testTag("LoadingIndicator"),
        color = MaterialTheme.colors.secondary
    )
}

@Preview(
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewLoadingView() {
    TheMovieDatabaseTheme {
        Surface {
            LoadingView(Modifier.fillMaxWidth())
        }
    }
}