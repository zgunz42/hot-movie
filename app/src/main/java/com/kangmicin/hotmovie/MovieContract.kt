package com.kangmicin.hotmovie

import com.kangmicin.hotmovie.model.Movie

interface MovieContract {
    interface Presenter {
        fun loadMovies()
    }

    abstract class ViewPresenter(val view: View): Presenter

    interface View {
        fun displayMovies(movies: List<Movie>)
    }
}