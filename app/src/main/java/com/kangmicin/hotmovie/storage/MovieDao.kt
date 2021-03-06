package com.kangmicin.hotmovie.storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kangmicin.hotmovie.data.Movie

/*
* TheMovieDb.org - Movie API
* */
class MovieDao {
    private val movieList = mutableListOf<Movie>()
    private val movies = MutableLiveData<List<Movie>>()
    private val errorMovieEvent = MutableLiveData<Boolean>()
    private val fetchMovieEvent = MutableLiveData<Boolean>()

    init {
        movies.value = movieList
    }


    fun addMovie(movie: Movie) {
        movieList.add(movie)
        movies.value = movieList
    }

    fun clearMovies() {
        movieList.clear()
        movies.value = emptyList()
    }

    fun toggleFetch(status: Boolean) {
        fetchMovieEvent.value = status
    }

    fun getFetchEvent() = fetchMovieEvent as LiveData<Boolean>

    fun toggleError(status: Boolean?) {
        errorMovieEvent.value = status
    }

    fun getErrorEvent() = errorMovieEvent as LiveData<Boolean>

    fun getMovies() = movies as LiveData<List<Movie>>
}