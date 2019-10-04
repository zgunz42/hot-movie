package com.kangmicin.hotmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kangmicin.hotmovie.model.Movie
import com.kangmicin.hotmovie.model.Person
import com.kangmicin.hotmovie.model.TvShow

class TvShowActivity : DetailActivity() {
    lateinit var tvShow: TvShow

    companion object {
        const val TV_SHOW_KEY = "TV_SHOW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShow = intent.getParcelableExtra(TV_SHOW_KEY)

        displayTitle(tvShow.title, tvShow.release)
        displayHeroPoster(tvShow.poster)
        displayGenres(tvShow.genre)
        displayMoviePoster(tvShow.poster)
        displayReleaseDate(tvShow.release)
        displayTopActor(tvShow.actors)
        displayInfoDirector(R.string.creator_format, tvShow.creators.map { it.name })
        displayPlot(tvShow.plot)
        displayInfoLength(tvShow.length)
    }
}
