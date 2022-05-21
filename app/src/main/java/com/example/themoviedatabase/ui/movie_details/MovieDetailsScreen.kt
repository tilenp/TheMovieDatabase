package com.example.themoviedatabase.ui.movie_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.themoviedatabase.model.domain.MovieDetails

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel
) {
    val uiState by viewModel.uiState.collectAsState(initial = MovieDetailsState.Empty)
    MovieImage(
        modifier = Modifier.height(300.dp),
        movie = uiState.movieDetails
    )
}

@Composable
private fun MovieImage(
    modifier: Modifier,
    movie: MovieDetails
) {
    val painter =
        rememberImagePainter(data = movie.backdropPath.medium) {
            crossfade(durationMillis = 200)
            placeholder(movie.backdropPath.placeholder)
            error(movie.backdropPath.backup)
        }
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = "Movie Image",
        contentScale = ContentScale.Crop
    )
}