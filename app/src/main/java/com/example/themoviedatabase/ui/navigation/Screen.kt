package com.example.themoviedatabase.ui.navigation

sealed class Screen(val route: String){
    object Movies: Screen("movies")
    object MovieDetails: Screen("movie_details")
}
