package com.example.themoviedatabase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.themoviedatabase.ui.movie_details.MovieDetailsScreen
import com.example.themoviedatabase.ui.movie_details.MovieDetailsViewModel
import com.example.themoviedatabase.ui.movies_screen.MoviesScreen
import com.example.themoviedatabase.ui.movies_screen.MoviesViewModel

@Composable
fun MainNavGraph(
    navController: NavHostController,
    moviesViewModel: MoviesViewModel,
    movieDetailsViewModel: MovieDetailsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Movies.route
    ) {
        composable(route = Screen.Movies.route) {
            MoviesScreen(
                viewModel = moviesViewModel,
                onMovieClick = { movieId ->
                    moviesViewModel.newAction(MoviesViewModel.Action.SelectMovie(movieId))
                    navController.navigate(Screen.MovieDetails.route)
                }
            )
        }
        composable(route = Screen.MovieDetails.route) {
            MovieDetailsScreen(
                viewModel = movieDetailsViewModel
            )
        }
    }
}