package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.use_case.UpdateMovieDetailsUseCase
import com.example.themoviedatabase.use_case.UpdateSimilarMoviesUseCase
import com.example.themoviedatabase.use_case.UpdateVideosUseCase
import com.example.themoviedatabase.use_case.impl.UpdateMovieDetailsUseCaseImpl
import com.example.themoviedatabase.use_case.impl.UpdateSimilarMoviesUseCaseImpl
import com.example.themoviedatabase.use_case.impl.UpdateVideosUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun bindsUpdateMovieDetailsUseCase(updateMovieDetailsUseCaseImpl: UpdateMovieDetailsUseCaseImpl): UpdateMovieDetailsUseCase

    @Binds
    fun bindsUpdateVideosUseCase(updateVideosUseCaseImpl: UpdateVideosUseCaseImpl): UpdateVideosUseCase

    @Binds
    fun bindsUpdateSimilarMoviesUseCase(updateSimilarMoviesUseCaseImpl: UpdateSimilarMoviesUseCaseImpl): UpdateSimilarMoviesUseCase
}