package com.example.themoviedatabase.dagger

import android.content.Context
import com.example.themoviedatabase.MainActivity
import com.example.themoviedatabase.dagger.module.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        DatabaseModule::class,
        MapperModule::class,
        RepositoryModule::class,
        ServiceModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}