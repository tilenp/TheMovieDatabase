package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.repository.impl.MovieRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindsMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}