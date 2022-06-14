package com.example.themoviedatabase.ui.movie_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.ui.common.CarouselView
import com.example.themoviedatabase.ui.common.VideoItemView

@Composable
fun MovieTrailersView(
    modifier: Modifier = Modifier,
    videos: List<Video>?,
    onVideoClick: (String) -> Unit
) {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    val trailerSize = dimensionResource(R.dimen.carousel_image_size)
    CarouselView(
        modifier = modifier,
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