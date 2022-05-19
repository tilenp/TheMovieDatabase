package com.example.themoviedatabase.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier
            .wrapContentSize(Alignment.Center)
            .padding(16.dp)
            .testTag("LoadingIndicator")
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