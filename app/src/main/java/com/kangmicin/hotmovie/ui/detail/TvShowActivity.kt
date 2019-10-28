package com.kangmicin.hotmovie.ui.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.Tv
import com.kangmicin.hotmovie.ui.main.TvsViewModel
import com.kangmicin.hotmovie.ui.main.TvsViewModelFactory
import com.kangmicin.hotmovie.utilities.InjectorUtils

class TvShowActivity : DetailActivity() {
    private lateinit var tv: Tv
    private lateinit var tvFactory: TvsViewModelFactory
    private lateinit var tvModel: TvsViewModel

    companion object {
        const val TV_SHOW_KEY = "TV_SHOW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvFactory = InjectorUtils.provideTvViewModelFactory()
        tvModel = ViewModelProviders.of(this, tvFactory).get(TvsViewModel::class.java)

        tv = intent.getParcelableExtra(TV_SHOW_KEY)

        tvModel.getTv(tv.id).observe(this, Observer<Tv> {
            it?.let {
                initDisplay(it)
            }
        })

        tvModel.loadTv(tv.id)

        initDisplay(tv)
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
