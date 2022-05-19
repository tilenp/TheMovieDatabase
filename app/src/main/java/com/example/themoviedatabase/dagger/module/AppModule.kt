package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.utils.DispatcherProvider
import com.example.themoviedatabase.utils.RuntimeDispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesDispatcher(): DispatcherProvider {
        return RuntimeDispatcherProvider()
    }
}