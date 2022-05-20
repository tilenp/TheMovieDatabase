package com.example.themoviedatabase.dagger

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class MyJUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, FakeApplication::class.java.name, context)
    }
}