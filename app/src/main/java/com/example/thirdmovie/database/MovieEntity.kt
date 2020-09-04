package com.example.thirdmovie.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thirdmovie.network.Movie

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val voteCount: Int,
    val voteAverage: Double,
    val title: String,
    val popularity: Double,
    val posterPath: String,
    val backdropPath: String,
    val overview: String,
    val releaseDate: String
)

fun List<MovieEntity>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            voteCount = it.voteCount,
            id = it.id,
            voteAverage = it.voteAverage,
            title = it.title,
            popularity = it.popularity,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            overview = it.overview,
            releaseDate = it.releaseDate
        )
    }
}

fun Movie.toEntity() = MovieEntity(id, voteCount, voteAverage, title, popularity, posterPath, backdropPath, overview, releaseDate)