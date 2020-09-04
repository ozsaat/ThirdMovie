package com.example.thirdmovie.overview

import android.app.Application
import androidx.lifecycle.*
import com.example.thirdmovie.BuildConfig
import com.example.thirdmovie.database.getDatabase
import com.example.thirdmovie.network.Movie
import com.example.thirdmovie.network.MovieApi
import com.example.thirdmovie.network.MovieSortFilter
import com.example.thirdmovie.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MovieApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    private val apiKey: String = BuildConfig.MOVIEDB_KEY

    private val _status = MutableLiveData<MovieApiStatus>()

    val status: LiveData<MovieApiStatus>
    get() = _status

//    private val _movies = MutableLiveData<List<Movie>>()
//
//    val movies: LiveData<List<Movie>>
//    get() = _movies

    private val _navigateToSelectedMovie = MutableLiveData<Movie>()

    val navigateToSelectedMovie: LiveData<Movie>
    get() = _navigateToSelectedMovie

    private val repository = MovieRepository(getDatabase(application))

    init {
        getMovieResponse(MovieSortFilter.POPULAR)
    }

    fun getMovies() = repository.getMovies().asLiveData()

    private fun getMovieResponse(filter: MovieSortFilter) {
        viewModelScope.launch {
            var getMoviesDeferred = MovieApi.retrofitService.getPopularMoviesAsync("en", filter.value ,1, apiKey)

            try {
                _status.value = MovieApiStatus.LOADING
                val listResult = getMoviesDeferred.await()
                repository.insert(listResult.results)
                _status.value = MovieApiStatus.DONE
//                val listOfMovies: List<Movie> = listResult.results
//                _movies.value = listOfMovies

            } catch (e: java.lang.Exception) {
                _status.value = MovieApiStatus.ERROR
            }
        }
    }

    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

}