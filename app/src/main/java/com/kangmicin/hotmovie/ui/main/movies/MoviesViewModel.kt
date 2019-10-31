package com.kangmicin.hotmovie.ui.main.movies

import androidx.lifecycle.ViewModel
import com.kangmicin.hotmovie.repository.MovieRepository
import javax.inject.Inject

class MoviesViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    fun getMovies() = movieRepository.getItems()
    fun loadMovie(id: Long) {

    }
}