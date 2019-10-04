package com.kangmicin.hotmovie

import com.kangmicin.hotmovie.model.Movie
import com.kangmicin.hotmovie.model.TvShow

interface Contract {
    interface Presenter {
        fun loadMovies()
        fun loadTvShows()
    }

    abstract class ViewPresenter(val view: View): Presenter

    interface View {
        fun displayMovies(movies: List<Movie>)
        fun displayTvShows(shows: List<TvShow>)
    }
}