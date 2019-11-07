package com.kangmicin.hotmovie.ui.main.tvs

import androidx.lifecycle.ViewModel
import com.kangmicin.hotmovie.data.entity.Tv
import com.kangmicin.hotmovie.repository.TvRepository
import javax.inject.Inject

class TvsViewModel @Inject constructor(private val tvShowRepository: TvRepository) : ViewModel() {
    fun getTvs() = tvShowRepository.getItems()
    fun loadTv(id: Long) = tvShowRepository.getItem(id)

    fun addToFavorite(tv: Tv){
        val update = tv.copy(isFavorite = true)
        tvShowRepository.updateItem(update)
    }
    fun removeFromFavorite(tv: Tv){
        val update = tv.copy(isFavorite = false)
        tvShowRepository.updateItem(update)
    }

    fun toggleFavorite(tv: Tv) {
        if (tv.isFavorite) {
            removeFromFavorite(tv)
        }else{
            addToFavorite(tv)
        }
    }
}