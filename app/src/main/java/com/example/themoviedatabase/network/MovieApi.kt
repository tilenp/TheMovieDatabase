package com.example.themoviedatabase.network

import com.example.themoviedatabase.BuildConfig.TMDB_BASE_URL
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.model.dto.PagingDTO
import com.example.themoviedatabase.model.dto.ResponseDTO
import com.example.themoviedatabase.model.dto.VideoDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("3/discover/movie")
    suspend fun getMovies(
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?,
    ): PagingDTO<MovieDTO>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long
    ): MovieDTO

    @GET("3/movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Long
    ): ResponseDTO<VideoDTO>

    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Long,
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
                .baseUrl(TMDB_BASE_URL)
                .client(client)
                .addConverterFactory(converterFactory)
                .build()
                .create(MovieApi::class.java)
        }
    }
}