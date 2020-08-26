package com.example.thirdmovie.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL: String = "https://api.themoviedb.org/3/"

enum class MovieSortFilter(val value: String) {POPULAR("popularity.desc"), RELEASE_DATE("release_date.desc")}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

val client : OkHttpClient = OkHttpClient.Builder().apply {
    this.addInterceptor(interceptor)
}.build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface MovieApiService {
    @GET("discover/movie")
    fun getMoviesAsync(@Query("language") language: String, @Query("sort_by") sortBy: String, @Query("api_key") apiKey: String):
    Deferred<List<Movie>>

    @GET("discover/movie")
    fun getPopularMoviesAsync(
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String

    ) : Deferred<MovieResponse>
}

object MovieApi {
    val retrofitService : MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}