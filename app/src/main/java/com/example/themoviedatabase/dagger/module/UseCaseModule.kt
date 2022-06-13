package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.use_case.UpdateMovieDetailsUseCase
import com.example.themoviedatabase.use_case.UpdateMovieVideosUseCase
import com.example.themoviedatabase.use_case.impl.UpdateMovieDetailsUseCaseImpl
import com.example.themoviedatabase.use_case.impl.UpdateMovieVideosUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun bindsUpdateMovieDetailsUseCase(updateMovieDetailsUseCaseImpl: UpdateMovieDetailsUseCaseImpl): UpdateMovieDetailsUseCase

    @Binds
    fun bindsUpdateMovieVideosUseCase(updateMovieVideosUseCaseImpl: UpdateMovieVideosUseCaseImpl): UpdateMovieVideosUseCase
}