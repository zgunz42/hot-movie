package com.kangmicin.hotmovie.ui.main

import androidx.lifecycle.ViewModel
import com.kangmicin.hotmovie.repository.MovieRepository

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies() = movieRepository.getMovies()

    fun loadMovies() = movieRepository.loadServiceMovies()
}