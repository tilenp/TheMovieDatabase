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
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.ui.common.CarouselView
import com.example.themoviedatabase.ui.common.LoadingView
import com.example.themoviedatabase.ui.common.MovieItemView
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import com.example.themoviedatabase.utils.UIText
import com.example.themoviedatabase.utils.UIValue

@Composable
fun SimilarMoviesView(
    modifier: Modifier = Modifier,
    movies: List<MovieSummary>?,
    onMovieClick: (Long) -> Unit = {}
) {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    val spacingXL = dimensionResource(R.dimen.spacing_xl)
    val imageSize = dimensionResource(R.dimen.image_size)
    if (movies == null) {
        LoadingView(
            modifier = modifier
                .testTag("LoadingView")
        )
    } else if (movies.isNotEmpty()) {
        CarouselView(
            modifier = modifier
                .padding(top = spacingXL),
            title = stringResource(R.string.Similar_movies),
            paddingValues = PaddingValues(start = spacingS, end = spacingS),
            horizontalArrangement = Arrangement.spacedBy(spacingS),
            list = movies,
            itemView = { movie ->
                MovieItemView(
                    modifier = Modifier
                        .height(imageSize)
                        .aspectRatio(ratio = 9f.div(16))
                        .testTag("MovieItem"),
                    movieId = movie.movieId,
                    title = movie.title,
                    posterPath = movie.posterPath,
                    rating = movie.rating.formattedValue,
                    onMovieClick = onMovieClick
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
                SimilarMoviesView(
                    movies = previewSimilarMovies(),
                    onMovieClick = {}
                )
            }
        }
    }
}

private fun previewSimilarMovies(): List<MovieSummary> {
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
                formattedValue = "6.7"
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
                formattedValue = "7.7"
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
                formattedValue = "7.2"
            )
        )
    )
}