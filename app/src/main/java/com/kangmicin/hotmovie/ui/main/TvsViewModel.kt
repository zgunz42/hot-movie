package com.kangmicin.hotmovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kangmicin.hotmovie.data.Movie
import com.kangmicin.hotmovie.data.Tv
import com.kangmicin.hotmovie.repository.TvRepository

class TvsViewModel (private val tvShowRepository: TvRepository) : ViewModel() {

    fun getTvs() = tvShowRepository.getTvShows()

    fun loadTvShows() = tvShowRepository.loadServiceTvShow()

    fun loadTv(id: Int) = tvShowRepository.loadServiceTv(id)

    fun getTv(id: Int): LiveData<Tv> {
        return Transformations.map(getTvs()) {
            it.single { m ->  m.id == id }
        }
    }
}