package com.example.themoviedatabase.dagger

import android.app.Application

class MovieApplication : Application(), ComponentProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    override fun provideAppComponent(): AppComponent {
        return appComponent
    }
}