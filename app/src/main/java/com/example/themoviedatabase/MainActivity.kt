package com.example.themoviedatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.themoviedatabase.dagger.ComponentProvider
import com.example.themoviedatabase.ui.movie_details.MovieDetailsViewModel
import com.example.themoviedatabase.ui.movies_screen.MoviesViewModel
import com.example.themoviedatabase.ui.navigation.MainNavGraph
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as ComponentProvider).provideAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            TheMovieDatabaseTheme {
                SetUpSystemUI()
                MainNavGraph(
                    widthSizeClass = widthSizeClass,
                    navController = rememberNavController(),
                    moviesViewModel = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java],
                    movieDetailsViewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailsViewModel::class.java],
                )
            }
        }
    }
}

@Composable
private fun SetUpSystemUI() {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.surface
    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
        systemUiController.setNavigationBarColor(
            color = systemBarColor
        )
    }
}