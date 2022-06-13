package com.example.themoviedatabase

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.themoviedatabase.dagger.ComponentProvider
import com.example.themoviedatabase.ui.movie_details.MovieDetailsViewModel
import com.example.themoviedatabase.ui.movies_screen.MoviesViewModel
import com.example.themoviedatabase.ui.navigation.MainNavGraph
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as ComponentProvider).provideAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setUpViewModels()
        showContent()
        setUpActions()
    }

    private fun setUpViewModels() {
        moviesViewModel = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]
        movieDetailsViewModel =
            ViewModelProvider(this, viewModelFactory)[MovieDetailsViewModel::class.java]
    }

    private fun showContent() {
        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            TheMovieDatabaseTheme {
                SetUpSystemUI()
                MainNavGraph(
                    widthSizeClass = widthSizeClass,
                    navController = rememberNavController(),
                    moviesViewModel = moviesViewModel,
                    movieDetailsViewModel = movieDetailsViewModel,
                )
            }
        }
    }

    private fun setUpActions() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailsViewModel.actions.collect { handleAction(it) }
            }
        }
    }

    private fun handleAction(action: MovieDetailsViewModel.Action) {
        when (action) {
            is MovieDetailsViewModel.Action.SelectVideo -> onVideoClick(action.url)
        }
    }

    private fun onVideoClick(videoUrl: String) {
        val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        startActivity(playVideoIntent)
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