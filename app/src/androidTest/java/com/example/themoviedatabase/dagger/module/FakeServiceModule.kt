package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.service.MovieService
import com.example.themoviedatabase.service.impl.MovieServiceImpl
import dagger.Binds
import dagger.Module

@Module
interface FakeServiceModule {

    @Binds
    fun bindsMovieService(movieServiceImpl: MovieServiceImpl): MovieService
}