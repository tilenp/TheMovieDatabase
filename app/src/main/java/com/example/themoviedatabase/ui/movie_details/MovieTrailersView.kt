package com.example.themoviedatabase.ui.movie_details

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.ui.common.CarouselView
import com.example.themoviedatabase.ui.common.LoadingView
import com.example.themoviedatabase.ui.common.VideoItemView
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme

@Composable
fun MovieTrailersView(
    modifier: Modifier = Modifier,
    videos: List<Video>?,
    onVideoClick: (String) -> Unit
) {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    val spacingXL = dimensionResource(R.dimen.spacing_xl)
    val trailerSize = dimensionResource(R.dimen.carousel_image_size)
    if (videos == null) {
        LoadingView(
            modifier = modifier
                .testTag("LoadingView")
        )
    } else if (videos.isNotEmpty()) {
        CarouselView(
            modifier = modifier
                .padding(top = spacingXL),
            title = stringResource(R.string.Trailers),
            paddingValues = PaddingValues(start = spacingS, end = spacingS),
            horizontalArrangement = Arrangement.spacedBy(spacingS),
            list = videos,
            itemView = { video ->
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
private fun PreviewTrailersView() {
    TheMovieDatabaseTheme {
        TheMovieDatabaseTheme {
            Surface {
                MovieTrailersView(
                    videos = listOf(Video(), Video(), Video(), Video()),
                    onVideoClick = {}
                )
            }
        }
    }
}