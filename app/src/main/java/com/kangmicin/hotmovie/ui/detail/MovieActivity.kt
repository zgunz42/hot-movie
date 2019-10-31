package com.kangmicin.hotmovie.ui.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.entity.Movie
import com.kangmicin.hotmovie.ui.main.movies.MoviesViewModel
import com.kangmicin.hotmovie.utilities.ViewModelProviderFactory

class MovieActivity : DetailActivity() {
    lateinit var movie: Movie
    private lateinit var movieFactory: ViewModelProviderFactory<MoviesViewModel>
    private lateinit var movieModel: MoviesViewModel

    companion object {
        const val MOVIE_KEY = "MOVIE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieModel = ViewModelProviders.of(this, movieFactory).get(MoviesViewModel::class.java)

        movie = intent.getParcelableExtra(MOVIE_KEY)

        movieModel.loadMovie(movie.id)

//        movieModel.getMovie(movie.id).observe(this, Observer<Movie> {
//            if (it != null) {
//                initDisplay(it)
//            }
//        })
        initDisplay(movie)
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
