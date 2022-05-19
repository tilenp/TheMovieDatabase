package com.example.themoviedatabase.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    message: String,
    buttonsContent: @Composable () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (text, box) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(box.top)
                },
            text = message,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .constrainAs(box) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(top = 8.dp)
        ) {
            buttonsContent()
        }
    }
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
fun PreviewErrorView() {
    TheMovieDatabaseTheme {
        Surface {
            ErrorView(
                message = "message",
                modifier = Modifier.fillMaxWidth(),
                buttonsContent = {
                    MyButton(
                        title = "retry",
                        onClick = { }
                    )
                }
            )
        }
    }
}