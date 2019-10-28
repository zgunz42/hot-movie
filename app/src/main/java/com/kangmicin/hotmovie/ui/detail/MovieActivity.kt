package com.kangmicin.hotmovie.ui.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.Movie
import com.kangmicin.hotmovie.ui.main.MoviesViewModel
import com.kangmicin.hotmovie.ui.main.MoviesViewModelFactory
import com.kangmicin.hotmovie.utilities.InjectorUtils

class MovieActivity : DetailActivity() {
    private lateinit var movie: Movie
    private lateinit var movieFactory: MoviesViewModelFactory
    private lateinit var movieModel: MoviesViewModel

    companion object {
        const val MOVIE_KEY = "MOVIE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieFactory = InjectorUtils.provideMoviesViewModelFactory()
        movieModel = ViewModelProviders.of(this, movieFactory).get(MoviesViewModel::class.java)

        movie = intent.getParcelableExtra(MOVIE_KEY)

        movieModel.loadMovie(movie.id)

        movieModel.getMovie(movie.id).observe(this, Observer<Movie> {
            it?.let {
                initDisplay(it)
            }
        })
        initDisplay(movie)
    }

    private  fun initDisplay(movie: Movie) {
        displayTitle(movie.title, movie.release)
        displayHeroPoster(movie.backdrop)
        displayGenres(movie.genre)
        displayMoviePoster(movie.poster)
        displayRatings(movie.rating)
        displayInfoReleaseDate(movie.release)
        displayTopActor(movie.actors)
        displayPlot(movie.plot)
        displayInfoLength(movie.length)

        movie.directors.forEach{
            displayInfoDirector(R.string.director_format, listOf(it.name))
        }
    }
}
