package com.kangmicin.hotmovie.storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kangmicin.hotmovie.data.Tv

class TvShowDao {
    private val tvShowList = mutableListOf<Tv>()
    private val tvShows = MutableLiveData<List<Tv>>()
    private val errorMovieEvent = MutableLiveData<Boolean>()
    private val fetchTvEvent = MutableLiveData<Boolean>()

    init {
        tvShows.value = tvShowList
    }


    fun addMovie(tv: Tv) {
        tvShowList.add(tv)
        tvShows.value = tvShowList
    }

    fun clearMovies() {
        tvShowList.clear()
        tvShows.value = emptyList()
    }

    fun getTvShows() = tvShows as LiveData<List<Tv>>

    fun toggleFetch(status: Boolean) {
        fetchTvEvent.value = status
    }

    fun getFetchEvent() = fetchTvEvent as LiveData<Boolean>

    fun toggleError(status: Boolean?) {
        errorMovieEvent.value = status
    }

    fun getErrorEvent() = errorMovieEvent as LiveData<Boolean>
}