package com.example.themoviedatabase.network

import com.example.themoviedatabase.BuildConfig.TMDB_API_KEY
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieAuthorizationInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val newHttpUrl = originalHttpUrl.newBuilder()
            .setQueryParameter("api_key", TMDB_API_KEY)
            .build()
        val newRequest: Request = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()

        return chain.proceed(newRequest)
    }
}