package com.kangmicin.hotmovie.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.entity.Movie
import com.kangmicin.hotmovie.ui.main.movies.MoviesViewModel
import javax.inject.Inject

class MovieActivity : DetailActivity() {
    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private lateinit var movieModel: MoviesViewModel
    private lateinit var movie: Movie

    companion object {
        const val MOVIE_KEY = "MOVIE"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_favorite -> {
                Log.i("favorite", "toggle")
                movieModel.toggleFavorite(movie)
                true
            }

            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getLongExtra(MOVIE_KEY, 0)
        movieModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)

        movieModel.loadMovie(id).observe(this, Observer {
            if (it.id == id) {
                movie = it
                Log.i("favorite", it.isFavorite.toString())
                initDisplay()
            }
        })
    }

    private  fun initDisplay() {
        setFavorite(movie.isFavorite)
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
