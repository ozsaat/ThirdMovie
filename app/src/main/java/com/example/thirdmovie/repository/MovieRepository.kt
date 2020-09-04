package com.example.thirdmovie.repository

import androidx.lifecycle.map
import com.example.thirdmovie.database.MovieDatabase
import com.example.thirdmovie.database.asDomainModel
import com.example.thirdmovie.database.toEntity
import com.example.thirdmovie.network.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(private val database: MovieDatabase) {
    suspend fun insert(movies: List<Movie>) = database.movieDao.insertAll(movies.map { it.toEntity() })
    fun getMovies(): Flow<List<Movie>> = database.movieDao.getMovies().map { it.asDomainModel() }
}