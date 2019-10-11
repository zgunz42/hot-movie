package com.kangmicin.hotmovie.repository

import com.kangmicin.hotmovie.network.TvDataSource
import com.kangmicin.hotmovie.storage.TvShowDao
import com.kangmicin.hotmovie.utilities.AppExecutors

class TvRepository private constructor(
    private val appExecutors: AppExecutors,
    private val dao: TvShowDao
){

    companion object {
        private const val ENDPOINT = "/discover/movie"

        @Volatile private var instance: TvRepository? = null

        fun getInstance(appExecutors: AppExecutors, dao: TvShowDao) =
            instance ?: synchronized(this) {
                instance
                    ?: TvRepository(appExecutors, dao).also { instance = it }
            }
    }

    fun loadServiceTvShow() {
        val service = TvDataSource.getInstance(appExecutors, dao)

        service.fetchDiscoverTv()
    }

    fun getTvShows() = dao.getTvShows()
}