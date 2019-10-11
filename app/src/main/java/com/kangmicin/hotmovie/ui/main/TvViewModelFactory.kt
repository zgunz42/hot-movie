package com.kangmicin.hotmovie.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kangmicin.hotmovie.repository.TvRepository

class TvViewModelFactory (private val tvRepository: TvRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TvViewModel(tvRepository) as T
    }
}