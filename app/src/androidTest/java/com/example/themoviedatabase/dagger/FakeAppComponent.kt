package com.example.themoviedatabase.dagger

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.example.themoviedatabase.dagger.module.*
import com.example.themoviedatabase.service.impl.MovieRemoteMediatorTest
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FakeApiModule::class,
        FakeAppModule::class,
        FakeDatabaseModule::class,
        FakeMapperModule::class,
        FakeRepositoryModule::class,
        FakeServiceModule::class,
        FakeViewModelModule::class
    ]
)
interface FakeAppComponent : AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): FakeAppComponent
    }

    // service
    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    fun inject(movieRemoteMediatorTest: MovieRemoteMediatorTest)
}
