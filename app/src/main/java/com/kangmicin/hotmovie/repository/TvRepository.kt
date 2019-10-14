package com.kangmicin.hotmovie.repository

import com.kangmicin.hotmovie.network.TvDataSource
import com.kangmicin.hotmovie.storage.TvShowDao
import com.kangmicin.hotmovie.utilities.AppExecutors

class TvRepository private constructor(
    appExecutors: AppExecutors,
    private val dao: TvShowDao
){
    private val service = TvDataSource.getInstance(appExecutors, dao)

    companion object {
        @Volatile private var instance: TvRepository? = null

        fun getInstance(appExecutors: AppExecutors, dao: TvShowDao) =
            instance ?: synchronized(this) {
                instance
                    ?: TvRepository(appExecutors, dao).also { instance = it }
            }
    }

    fun loadServiceTvShow() = service.fetchDiscoverTv()

    fun getTvShows() = dao.getTvShows()

    fun clearError() = dao.toggleError(false)

    fun errorEvent() = dao.getErrorEvent()

    fun fetchEvent() = dao.getFetchEvent()

    fun loadServiceTv(id: Int) = service.fetchTvDetail(id)
}