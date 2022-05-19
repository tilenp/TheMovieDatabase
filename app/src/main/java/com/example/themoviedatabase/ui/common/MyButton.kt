package com.example.themoviedatabase.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    title: String?,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = colors,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title.orEmpty(),
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    name = "Light Mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun PreviewHireButton() {
    TheMovieDatabaseTheme {
        MyButton(
            title = "retry",
            onClick = { }
        )
    }
}