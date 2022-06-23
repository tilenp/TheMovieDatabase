package com.example.themoviedatabase.dagger

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.example.themoviedatabase.dagger.module.*
import com.example.themoviedatabase.end_to_end.SelectMovieTest
import com.example.themoviedatabase.service.impl.MovieRemoteMediatorTest
import com.example.themoviedatabase.ui.movie_details.MovieDetailsScreenTest
import com.example.themoviedatabase.ui.movies_screen.MoviesScreenTest
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FakeApiModule::class,
        FakeAppModule::class,
        FakeCacheModule::class,
        FakeDatabaseModule::class,
        FakeMapperModule::class,
        FakeRepositoryModule::class,
        FakeServiceModule::class,
        FakeUseCaseModule::class,
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

    // screen
    fun inject(moviesScreenTest: MoviesScreenTest)
    fun inject(movieDetailsScreenTest: MovieDetailsScreenTest)

    // end to end
    fun inject(selectMovieTest: SelectMovieTest)
}
