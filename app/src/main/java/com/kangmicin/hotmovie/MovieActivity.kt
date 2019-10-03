package com.kangmicin.hotmovie

import android.os.Bundle
import com.kangmicin.hotmovie.model.Movie

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
        displayReleaseDate(movie.release)
        displayTopActor(movie.actors)
        displayInfoDirector(listOf(movie.director.name))
        displayPlot(movie.plot)
        displayInfoLength(movie.length)
    }
}
