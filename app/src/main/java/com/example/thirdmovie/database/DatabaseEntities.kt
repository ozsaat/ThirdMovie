package com.example.thirdmovie.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thirdmovie.network.Movie

@Entity
data class DatabasePopularMovies constructor(
    val voteCount: Int,
    @PrimaryKey
    val id: Int,
    val voteAverage: Double,
    val title: String,
    val popularity: Double,
    val posterPath: String,
    val backdropPath: String,
    val overview: String,
    val releaseDate: String
)

fun List<DatabasePopularMovies>.asDomainModel(): List<Movie> {
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