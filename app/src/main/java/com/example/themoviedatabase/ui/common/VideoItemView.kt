package com.example.themoviedatabase.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.Video

@Composable
fun VideoItemView(
    modifier: Modifier = Modifier,
    video: Video,
    onVideoClick: (String) -> Unit = {}
) {
    val spacingM = dimensionResource(R.dimen.spacing_m)
    val painter = rememberImagePainter(data = video.thumbnailMQ) {
        crossfade(durationMillis = 200)
        placeholder(R.drawable.ic_photo)
        error(R.drawable.ic_photo)
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(spacingM),
    ) {
        Box(
            modifier = Modifier
                .clickable { onVideoClick(video.videoUrl) }
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painter,
                contentDescription = "Movie Image",
                contentScale = ContentScale.FillWidth
            )
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun MovieTrailerWidgetPreview() {
    val trailerSize = dimensionResource(R.dimen.carousel_image_size)
    VideoItemView(
        modifier = Modifier
            .width(trailerSize)
            .aspectRatio(ratio = 16f.div(9)),
        video = Video(
            id = "",
            key = "XHk5kCIiGoM"
        )
    )
}