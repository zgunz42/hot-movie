package com.kangmicin.hotmovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kangmicin.hotmovie.data.Movie
import com.kangmicin.hotmovie.repository.MovieRepository

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies() = movieRepository.getMovies()

    fun loadMovies() = movieRepository.loadServiceMovies()

    fun loadMovie(id: Int) = movieRepository.loadServiceMovie(id)

    fun getMovie(id: Int): LiveData<Movie> {
        return Transformations.switchMap(movieRepository.fetchEvent()) {
            val result = MediatorLiveData<Movie>()
            result.addSource(getMovies()) {
                it.forEach { m ->
                    if (m.id == id) {
                        result.value = m
                    }
                }
            }
            result
        }
    }

    fun hasLoading() = movieRepository.fetchEvent()

    fun hasError(): LiveData<Boolean> {
        return Transformations.map(movieRepository.errorEvent()) { inError ->
            if (inError) {
                movieRepository.clearError()
            }
            inError
        }
    }
}