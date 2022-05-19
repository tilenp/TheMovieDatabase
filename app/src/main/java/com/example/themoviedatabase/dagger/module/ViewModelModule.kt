package com.example.themoviedatabase.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedatabase.dagger.MyViewModelFactory
import com.example.themoviedatabase.dagger.ViewModelKey
import com.example.themoviedatabase.ui.movies_screen.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Singleton
    @Binds
    fun bindViewModelFactory(viewModelFactory: MyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    fun bindMoviesViewModel(viewModel: MoviesViewModel): ViewModel
}
