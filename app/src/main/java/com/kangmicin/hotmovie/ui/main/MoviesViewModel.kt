package com.kangmicin.hotmovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kangmicin.hotmovie.data.Movie
import com.kangmicin.hotmovie.repository.MovieRepository

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies() = movieRepository.getMovies()

    fun loadMovies() = movieRepository.loadServiceMovies()

    fun loadMovie(id: Int) = movieRepository.loadServiceMovie(id)

    fun getMovie(id: Int): LiveData<Movie> {
        return Transformations.map(getMovies()) {
            it.single { m ->  m.id == id }
        }
    }
}