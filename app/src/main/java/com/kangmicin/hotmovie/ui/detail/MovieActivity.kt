package com.kangmicin.hotmovie.ui.detail

import android.os.Bundle
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.Movie

class MovieActivity : DetailActivity() {
    lateinit var movie: Movie

    companion object {
        const val MOVIE_KEY = "MOVIE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movie = intent.getParcelableExtra(MOVIE_KEY)

        displayTitle(movie.title, movie.release)
        displayHeroPoster(movie.poster)
        displayGenres(movie.genre)
        displayMoviePoster(movie.poster)
        displayRatings(movie.rating)
        displayInfoReleaseDate(movie.release)
        displayTopActor(movie.actors)
        displayPlot(movie.plot)
        displayInfoLength(movie.length)

        movie.director?.let {
            displayInfoDirector(R.string.director_format, listOf(it.name))
        }
    }
}
