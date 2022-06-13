package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.service.MovieDetailsService
import com.example.themoviedatabase.service.MovieService
import com.example.themoviedatabase.service.MovieVideosService
import com.example.themoviedatabase.service.impl.MovieDetailsServiceImpl
import com.example.themoviedatabase.service.impl.MovieServiceImpl
import com.example.themoviedatabase.service.impl.MovieVideosServiceImpl
import dagger.Binds
import dagger.Module

@Module
interface ServiceModule {

    @Binds
    fun bindsMovieService(movieServiceImpl: MovieServiceImpl): MovieService

    @Binds
    fun bindsMovieDetailsService(movieDetailsServiceImpl: MovieDetailsServiceImpl): MovieDetailsService

    @Binds
    fun bindsMovieVideosService(movieVideosServiceImpl: MovieVideosServiceImpl): MovieVideosService
}