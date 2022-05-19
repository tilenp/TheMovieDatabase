package com.example.themoviedatabase.network

import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("3/discover/tv")
    suspend fun getMovies(
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?,
    ): PagingDTO<MovieDTO>

    companion object {
        fun create(
            logger: HttpLoggingInterceptor,
            authorizationInterceptor: MovieAuthorizationInterceptor,
            converterFactory: GsonConverterFactory
        ): MovieApi {
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(authorizationInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .client(client)
                .addConverterFactory(converterFactory)
                .build()
                .create(MovieApi::class.java)
        }
    }
}