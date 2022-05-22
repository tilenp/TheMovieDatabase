package com.example.themoviedatabase.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedatabase.dagger.MyViewModelFactory
import com.example.themoviedatabase.dagger.ViewModelKey
import com.example.themoviedatabase.ui.movie_details.MovieDetailsViewModel
import com.example.themoviedatabase.ui.movies_screen.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface FakeViewModelModule {

    @Singleton
    @Binds
    fun bindViewModelFactory(viewModelFactory: MyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    fun bindMoviesViewModel(viewModel: MoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    fun bindMovieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel
}
