package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.use_case.UpdateMovieDetailsUseCase
import com.example.themoviedatabase.use_case.impl.UpdateMovieDetailsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun bindsUpdateMovieDetailsUseCase(updateMovieDetailsUseCaseImpl: UpdateMovieDetailsUseCaseImpl): UpdateMovieDetailsUseCase
}