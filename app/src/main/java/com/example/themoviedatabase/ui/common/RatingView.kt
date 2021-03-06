package com.example.themoviedatabase.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.themoviedatabase.R
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme

@Composable
fun RatingView(
    modifier: Modifier = Modifier,
    rating: String,
    style: TextStyle,
    padding: Dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = MaterialTheme.colors.secondary,
                shape = CircleShape
            )
            .layout { measurable, constraints ->
                // Measure the composable
                val placeable = measurable.measure(constraints)

                //get the current max dimension to assign width=height
                val currentHeight = placeable.height
                var heightCircle = currentHeight
                if (placeable.width > heightCircle)
                    heightCircle = placeable.width

                //assign the dimension and the center position
                layout(heightCircle, heightCircle) {
                    // Where the composable gets placed
                    placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                }
            },
    ) {
        Text(
            text = rating,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.background,
            style = style,
            modifier = Modifier.padding(padding)
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
private fun PreviewRatingView() {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    TheMovieDatabaseTheme {
        Surface {
            RatingView(
                rating = "10.0",
                style = MaterialTheme.typography.caption,
                padding = spacingS
            )
        }
    }
}