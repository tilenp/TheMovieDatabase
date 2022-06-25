package com.example.themoviedatabase.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.themoviedatabase.R
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme

@Composable
fun MyTopBar(
    modifier: Modifier = Modifier,
    title: String
) {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    TopAppBar(
        modifier = modifier.testTag("MyTopBar"),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h5
            )
        },
        elevation = spacingS,
    )
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
private fun PreviewMyTopBar() {
    TheMovieDatabaseTheme {
        Surface {
            MyTopBar(title = "The Movie Database")
        }
    }
}
