package com.kangmicin.hotmovie

import com.kangmicin.hotmovie.data.Movie
import com.kangmicin.hotmovie.data.Tv

interface Contract {
    interface Presenter {
        fun loadMovies()
        fun loadTvShows()
    }

    abstract class ViewPresenter(val view: View): Presenter

    interface View {
        fun displayMovies(movies: List<Movie>)
        fun displayTvShows(shows: List<Tv>)
    }
}