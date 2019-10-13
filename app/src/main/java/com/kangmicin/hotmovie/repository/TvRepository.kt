package com.kangmicin.hotmovie.repository

import com.kangmicin.hotmovie.network.TvDataSource
import com.kangmicin.hotmovie.storage.TvShowDao
import com.kangmicin.hotmovie.utilities.AppExecutors

class TvRepository private constructor(
    private val appExecutors: AppExecutors,
    private val dao: TvShowDao
){
    val service = TvDataSource.getInstance(appExecutors, dao)

    companion object {
        private const val ENDPOINT = "/discover/movie"

        @Volatile private var instance: TvRepository? = null

        fun getInstance(appExecutors: AppExecutors, dao: TvShowDao) =
            instance ?: synchronized(this) {
                instance
                    ?: TvRepository(appExecutors, dao).also { instance = it }
            }
    }

    fun loadServiceTvShow() = service.fetchDiscoverTv()

    fun getTvShows() = dao.getTvShows()


    fun loadServiceTv(id: Int) = service.fetchTvDetail(id)
}