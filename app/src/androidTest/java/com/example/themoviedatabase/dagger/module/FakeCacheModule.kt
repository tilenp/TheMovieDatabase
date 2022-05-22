package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.cache.MovieCache
import com.example.themoviedatabase.cache.impl.MovieCacheImpl
import dagger.Binds
import dagger.Module

@Module
interface FakeCacheModule {

    @Binds
    fun bindsMovieCache(movieCacheImpl: MovieCacheImpl): MovieCache
}