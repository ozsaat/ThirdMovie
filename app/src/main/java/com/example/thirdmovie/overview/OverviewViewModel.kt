package com.example.thirdmovie.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thirdmovie.BuildConfig
import com.example.thirdmovie.network.Movie
import com.example.thirdmovie.network.MovieApi
import com.example.thirdmovie.network.MovieResponse
import com.example.thirdmovie.network.MovieSortFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MovieApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {
    private val apiKey: String = BuildConfig.MOVIEDB_KEY

    private val _status = MutableLiveData<MovieApiStatus>()

    val status: LiveData<MovieApiStatus>
    get() = _status

    private val _movies = MutableLiveData<List<Movie>>()

    val movies: LiveData<List<Movie>>
    get() = _movies

    private val _movieResponse = MutableLiveData<MovieResponse>()

    val movieResponse: LiveData<MovieResponse>
    get() = _movieResponse

    private val _navigateToSelectedMovie = MutableLiveData<Movie>()

    val navigateToSelectedMovie: LiveData<Movie>
    get() = _navigateToSelectedMovie

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMovieResponse(MovieSortFilter.POPULAR)
    }

    private fun getListOfMovies(filter: MovieSortFilter) {
        coroutineScope.launch {
            var getMoviesDeferred = MovieApi.retrofitService.getMoviesAsync("en", filter.value, apiKey)
            try {
                _status.value = MovieApiStatus.LOADING
                val listResult = getMoviesDeferred.await()
                _status.value = MovieApiStatus.DONE
                _movies.value = listResult
            } catch (e: Exception) {
                _status.value = MovieApiStatus.ERROR
                _movies.value = ArrayList()
            }
        }
    }

    private fun getMovieResponse(filter: MovieSortFilter): MovieResponse? {
        coroutineScope.launch {
            var getMoviesDeferred = MovieApi.retrofitService.getPopularMoviesAsync("en", filter.value ,1, apiKey)

            try {
                _status.value = MovieApiStatus.LOADING
                val listResult = getMoviesDeferred.await()
                _status.value = MovieApiStatus.DONE
                _movieResponse.value = listResult
                val listOfMovies: List<Movie> = listResult.results
                _movies.value = listOfMovies

            } catch (e: java.lang.Exception) {
                _status.value = MovieApiStatus.ERROR
                _movieResponse.value = null
            }
        }

        return _movieResponse.value
    }

    fun getMovieObjects() {
        val movieResponse : MovieResponse? = getMovieResponse(MovieSortFilter.POPULAR)
        _movies.value = movieResponse?.results
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    // TODO (06) Add updateFilter method that takes a filter input and re-gets the properties
    fun updateFilter(filter: MovieSortFilter) {
//        getListOfMovies(filter)
        getMovieResponse(MovieSortFilter.POPULAR)
    }

}