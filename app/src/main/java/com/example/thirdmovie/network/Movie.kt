package com.example.thirdmovie.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @Json(name = "vote_count")val voteCount: Int,
    @Json(name = "id")val id: Int,
    @Json(name = "vote_average")val voteAverage: Double,
    @Json(name = "title")val title: String,
    @Json(name = "popularity")val popularity: Double,
    @Json(name = "poster_path")val posterPath: String,
    @Json(name = "backdrop_path")val backdropPath: String,
    @Json(name = "overview")val overview: String,
    @Json(name = "release_date")val releaseDate: String) : Parcelable // Parcelable is Android. Make it serializable

data class MovieResponse (val page: Int, val results: List<Movie>)

//inline class MovieId(val id: Int)