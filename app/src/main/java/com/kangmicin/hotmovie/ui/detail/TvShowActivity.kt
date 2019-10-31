package com.kangmicin.hotmovie.ui.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.entity.Tv
import com.kangmicin.hotmovie.ui.main.tvs.TvsViewModel
import javax.inject.Inject

class TvShowActivity : DetailActivity() {
    lateinit var tv: Tv

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    companion object {
        const val TV_SHOW_KEY = "TV_SHOW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getLongExtra(TV_SHOW_KEY, 0)
        val tvModel = ViewModelProviders.of(this, factory).get(TvsViewModel::class.java)

        tvModel.loadTv(id).observe(this, Observer {
            if (it.id == id) {
                initDisplay(it)
            }
        })
    }

    private fun initDisplay(tv: Tv) {
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
