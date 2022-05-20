package com.example.themoviedatabase.dagger

import android.app.Application

class FakeApplication : Application(), ComponentProvider {

    private lateinit var fakeAppComponent: FakeAppComponent

    override fun onCreate() {
        super.onCreate()
        fakeAppComponent = DaggerFakeAppComponent.factory().create(this)
    }

    override fun provideAppComponent(): FakeAppComponent {
        return fakeAppComponent
    }
}