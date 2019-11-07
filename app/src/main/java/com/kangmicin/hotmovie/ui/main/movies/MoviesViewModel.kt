package com.kangmicin.hotmovie.ui.main.movies

import androidx.lifecycle.ViewModel
import com.kangmicin.hotmovie.data.entity.Movie
import com.kangmicin.hotmovie.repository.MovieRepository
import javax.inject.Inject

class MoviesViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    fun getMovies() = movieRepository.getItems()
    fun loadMovie(id: Long) = movieRepository.getItem(id)
    fun addToFavorite(movie: Movie){
        val update = movie.copy(isFavorite = true)
        movieRepository.updateItem(update)
    }
    fun removeFromFavorite(movie: Movie){
        val update = movie.copy(isFavorite = false)
        movieRepository.updateItem(update)
    }

    fun toggleFavorite(movie: Movie) {
        if (movie.isFavorite) {
            removeFromFavorite(movie)
        }else{
            addToFavorite(movie)
        }
    }
}