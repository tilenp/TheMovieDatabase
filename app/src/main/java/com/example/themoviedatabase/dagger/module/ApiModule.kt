package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.network.MovieApi
import com.example.themoviedatabase.network.MovieAuthorizationInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun providesLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Singleton
    @Provides
    fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory
            .create(gson)
    }

    @Singleton
    @Provides
    fun providesMovieApi(
        logger: HttpLoggingInterceptor,
        movieAuthorizationInterceptor: MovieAuthorizationInterceptor,
        converterFactory: GsonConverterFactory
    ): MovieApi {
        return MovieApi.create(
            logger,
            movieAuthorizationInterceptor,
            converterFactory
        )
    }
}