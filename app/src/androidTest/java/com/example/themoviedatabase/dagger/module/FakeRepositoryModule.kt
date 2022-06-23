package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.repository.VideoRepository
import com.example.themoviedatabase.repository.impl.MovieRepositoryImpl
import com.example.themoviedatabase.repository.impl.VideoRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface FakeRepositoryModule {

    @Binds
    fun bindsMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    fun bindsVideoRepository(videoRepositoryImpl: VideoRepositoryImpl): VideoRepository
}