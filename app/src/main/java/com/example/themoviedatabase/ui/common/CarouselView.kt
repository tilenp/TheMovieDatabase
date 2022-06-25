package com.example.themoviedatabase.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import com.example.themoviedatabase.utils.UIValue
import com.example.themoviedatabase.utils.UIText

@Composable
fun <T : Any> CarouselView(
    modifier: Modifier = Modifier,
    title: String,
    paddingValues: PaddingValues,
    horizontalArrangement: Arrangement.Horizontal,
    list: List<T>,
    itemView: @Composable (T) -> Unit
) {
    val spacingXL = dimensionResource(R.dimen.spacing_xl)
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(start = spacingXL)
                .testTag("CarouselView$title"),
            text = title,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        LazyRow(
            modifier = Modifier
                .padding(top = spacingXL),
            contentPadding = paddingValues,
            horizontalArrangement = horizontalArrangement,
        ) {
            items(list) { carouselItem ->
                itemView(carouselItem)
            }
        }
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
fun PreviewComposableCarousel() {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    val paddingValues = PaddingValues(start = spacingS, end = spacingS)
    val horizontalArrangement = Arrangement.spacedBy(spacingS)
    val imageSize = dimensionResource(R.dimen.carousel_image_size)
    TheMovieDatabaseTheme {
        Surface {
            CarouselView(
                title = "Popular",
                paddingValues = paddingValues,
                horizontalArrangement = horizontalArrangement,
                list = previewMovieSummaries(),
                itemView = { movie ->
                    MovieItemView(
                        modifier = Modifier.height(imageSize),
                        movieId = movie.movieId,
                        title = movie.title,
                        posterPath = movie.posterPath,
                        rating = movie.rating.formattedValue,
                        onMovieClick = {}
                    )
                }
            )
        }
    }
}

private fun previewMovieSummaries(): List<MovieSummary> {
    return listOf(
        MovieSummary(
            movieId = 752623,
            title = UIText("The Lost City"),
            posterPath = ImagePath(
                url = "/neMZH82Stu91d3iqvLdNQfqPPyl.jpg",
                placeholder = R.drawable.ic_photo,
                resourceId = R.drawable.ic_broken_image
            ),
            rating = UIValue(
                value = 6.7f,
                formattedValue = "6.7f"
            )
        ),
        MovieSummary(
            movieId = 675353,
            title = UIText("Sonic the Hedgehog 2"),
            posterPath = ImagePath(
                url = "/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
                placeholder = R.drawable.ic_photo,
                resourceId = R.drawable.ic_broken_image
            ),
            rating = UIValue(
                value = 7.7f,
                formattedValue = "7.7f"
            )
        ),
        MovieSummary(
            movieId = 752623,
            title = UIText("Uncharted"),
            posterPath = ImagePath(
                url = "/tlZpSxYuBRoVJBOpUrPdQe9FmFq.jpg",
                placeholder = R.drawable.ic_photo,
                resourceId = R.drawable.ic_broken_image
            ),
            rating = UIValue(
                value = 7.2f,
                formattedValue = "7.2f"
            )
        )
    )
}