package com.kangmicin.hotmovie.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.Movie
import com.kangmicin.hotmovie.ui.main.MoviesViewModel
import com.kangmicin.hotmovie.ui.main.MoviesViewModelFactory
import com.kangmicin.hotmovie.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_detail.*

class MovieActivity : DetailActivity() {
    lateinit var movie: Movie
    lateinit var movieFactory: MoviesViewModelFactory
    lateinit var movieModel: MoviesViewModel

    companion object {
        const val MOVIE_KEY = "MOVIE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieFactory = InjectorUtils.provideMoviesViewModelFactory()
        movieModel = ViewModelProviders.of(this, movieFactory).get(MoviesViewModel::class.java)

        movie = intent.getParcelableExtra(MOVIE_KEY)

        movieModel.getMovie(movie.id).observe(this, Observer<Movie> {
            if (it != null) {
                Log.i("ThreadNetwork", "" + movie.length + movie.actors.values)
                initDisplay(it)
                toolbar.postInvalidate()
                toolbar.requestLayout()
            }
            Log.i("ThreadNetwork", "getMovie@complete")
        })

        movieModel.loadMovie(movie.id)

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
