package com.kangmicin.hotmovie.ui.detail

import android.os.Bundle
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.Tv

class TvShowActivity : DetailActivity() {
    lateinit var tv: Tv

    companion object {
        const val TV_SHOW_KEY = "TV_SHOW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv = intent.getParcelableExtra(TV_SHOW_KEY)

        displayTitle(tv.name, tv.release)
        displayHeroPoster(tv.backdrop)
        displayGenres(tv.genre)
        displayMoviePoster(tv.poster)
        displayInfoReleaseDate(tv.release)
        displayTopActor(tv.actors)
        displayInfoDirector(R.string.creator_format, tv.creators.map { it.name })
        displayPlot(tv.plot)
        displayInfoLength(tv.length)
    }
}
