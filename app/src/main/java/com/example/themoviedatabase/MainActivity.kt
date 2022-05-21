package com.example.themoviedatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.themoviedatabase.dagger.ComponentProvider
import com.example.themoviedatabase.ui.movie_details.MovieDetailsViewModel
import com.example.themoviedatabase.ui.movies_screen.MoviesViewModel
import com.example.themoviedatabase.ui.navigation.MainNavGraph
import com.example.themoviedatabase.ui.theme.TheMovieDatabaseTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as ComponentProvider).provideAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDatabaseTheme {
                MainNavGraph(
                    navController = rememberNavController(),
                    moviesViewModel = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java],
                    movieDetailsViewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailsViewModel::class.java],
                )
            }
        }
    }
}