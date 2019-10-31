package com.kangmicin.hotmovie.ui.main.tvs

import androidx.lifecycle.ViewModel
import com.kangmicin.hotmovie.repository.TvRepository
import javax.inject.Inject

class TvsViewModel @Inject constructor(private val tvShowRepository: TvRepository) : ViewModel() {
    fun getTvs() = tvShowRepository.getItems()
    fun loadTv(id: Long) {}
}