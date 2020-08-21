package com.example.thirdmovie.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val voteCount: Int,
    val id: Int,
    val voteAverage: Double,
    val title: String,
    val popularity: Double,
    @Json(name = "poster_path")val posterPath: String,
    @Json(name = "backdrop_path")val backdropPath: String,
    val overview: String,
    val releaseDate: String) : Parcelable {
}