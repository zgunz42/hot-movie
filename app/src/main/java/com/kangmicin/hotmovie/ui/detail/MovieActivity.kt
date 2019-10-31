package com.kangmicin.hotmovie.ui.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.entity.Movie
import com.kangmicin.hotmovie.ui.main.movies.MoviesViewModel
import javax.inject.Inject

class MovieActivity : DetailActivity() {
//    lateinit var movie: Movie

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    companion object {
        const val MOVIE_KEY = "MOVIE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getLongExtra(MOVIE_KEY, 0)
        val movieModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)

        movieModel.loadMovie(id).observe(this, Observer {
            if (it.id == id) {
                initDisplay(it)
            }
        })
    }

    private  fun initDisplay(movie: Movie) {
        displayTitle(movie.name, movie.release)
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
