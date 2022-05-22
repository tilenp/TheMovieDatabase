package com.example.themoviedatabase.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.themoviedatabase.R
import com.example.themoviedatabase.ui.common.MyTopBar
import com.example.themoviedatabase.ui.movie_details.MovieDetailsScreen
import com.example.themoviedatabase.ui.movie_details.MovieDetailsState
import com.example.themoviedatabase.ui.movie_details.MovieDetailsViewModel
import com.example.themoviedatabase.ui.movies_screen.MovieListContent
import com.example.themoviedatabase.ui.movies_screen.MoviesViewModel

@Composable
fun MainNavGraph(
    widthSizeClass: WindowWidthSizeClass,
    navController: NavHostController,
    moviesViewModel: MoviesViewModel,
    movieDetailsViewModel: MovieDetailsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Movies.route
    ) {
        composable(route = Screen.Movies.route) {
            ConfigureMoviesScreen(
                widthSizeClass = widthSizeClass,
                navController = navController,
                moviesViewModel = moviesViewModel,
                movieDetailsViewModel = movieDetailsViewModel
            )
        }
        composable(route = Screen.MovieDetails.route) {
            ConfigureMovieDetailScreen(
                widthSizeClass = widthSizeClass,
                navController = navController,
                moviesViewModel = moviesViewModel,
                movieDetailsViewModel = movieDetailsViewModel
            )
        }
    }
}

@Composable
private fun ConfigureMoviesScreen(
    widthSizeClass: WindowWidthSizeClass,
    navController: NavHostController,
    moviesViewModel: MoviesViewModel,
    movieDetailsViewModel: MovieDetailsViewModel
) {
    Scaffold(
        topBar = {
            MyTopBar(
                title = stringResource(R.string.app_name)
            )
        },
        content = { padding ->
            Row(
                modifier = Modifier.padding(padding)
            ) {
                val columns = when (widthSizeClass) {
                    WindowWidthSizeClass.Compact -> 2
                    WindowWidthSizeClass.Medium -> 3
                    else -> 2
                }
                val pagedMovies = moviesViewModel.movies.collectAsLazyPagingItems()
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    MovieListContent(
                        columns = columns,
                        pagesMovies = pagedMovies,
                        onMovieClick = { movieId ->
                            moviesViewModel.newAction(MoviesViewModel.Action.SelectMovie(movieId))
                            if (widthSizeClass != WindowWidthSizeClass.Expanded) {
                                navController.navigate(Screen.MovieDetails.route)
                            }
                        },
                        getErrorMessageId = { moviesViewModel.getErrorMessage(it) }
                    )
                }
                if (widthSizeClass == WindowWidthSizeClass.Expanded) {
                    val uiState by movieDetailsViewModel.uiState.collectAsState(initial = MovieDetailsState.Empty)
                    MovieDetailsScreen(
                        widthSizeClass = widthSizeClass,
                        modifier = Modifier.weight(1f),
                        uiState = uiState,
                        onBackButtonClicked = { navController.popBackStack() }
                    )
                }
            }
        }
    )
}

@Composable
private fun ConfigureMovieDetailScreen(
    widthSizeClass: WindowWidthSizeClass,
    navController: NavHostController,
    moviesViewModel: MoviesViewModel,
    movieDetailsViewModel: MovieDetailsViewModel
) {
    Scaffold(
        topBar = {
            if (widthSizeClass == WindowWidthSizeClass.Expanded) {
                MyTopBar(
                    title = stringResource(R.string.app_name)
                )
            }
        },
        content = { padding ->
            Row(
                modifier = Modifier.padding(padding)
            ) {
                if (widthSizeClass == WindowWidthSizeClass.Expanded) {
                    val columns = when (widthSizeClass) {
                        WindowWidthSizeClass.Compact -> 2
                        WindowWidthSizeClass.Medium -> 3
                        else -> 2
                    }
                    val pagedMovies = moviesViewModel.movies.collectAsLazyPagingItems()
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        MovieListContent(
                            columns = columns,
                            pagesMovies = pagedMovies,
                            onMovieClick = { movieId ->
                                moviesViewModel.newAction(MoviesViewModel.Action.SelectMovie(movieId))
                            },
                            getErrorMessageId = { moviesViewModel.getErrorMessage(it) }
                        )
                    }
                }
                val uiState by movieDetailsViewModel.uiState.collectAsState(initial = MovieDetailsState.Empty)
                MovieDetailsScreen(
                    widthSizeClass = widthSizeClass,
                    modifier = Modifier.weight(1f),
                    uiState = uiState,
                    onBackButtonClicked = { navController.popBackStack() }
                )
            }
        }
    )
}