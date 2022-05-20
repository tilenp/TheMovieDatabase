package com.example.themoviedatabase.dagger.module

import androidx.test.espresso.idling.net.UriIdlingResource
import com.example.themoviedatabase.utils.DispatcherProvider
import com.example.themoviedatabase.utils.FakeDispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FakeAppModule {

    @Singleton
    @Provides
    fun providesDispatcher(): DispatcherProvider {
        return FakeDispatcherProvider()
    }

    @Singleton
    @Provides
    fun providesUriIdlingResource(): UriIdlingResource {
        return UriIdlingResource("Testing", 100)
    }
}