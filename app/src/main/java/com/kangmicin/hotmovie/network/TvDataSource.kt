package com.kangmicin.hotmovie.network

import com.kangmicin.hotmovie.network.poko.DiscoverMovie
import com.kangmicin.hotmovie.network.poko.DiscoverTv
import com.kangmicin.hotmovie.storage.MovieDao
import com.kangmicin.hotmovie.storage.TvShowDao
import com.kangmicin.hotmovie.utilities.AppExecutors
import io.reactivex.observers.DisposableObserver

class TvDataSource  private constructor(
    private val appExecutors: AppExecutors,
    private val dao: TvShowDao
){
    companion object {
        @Volatile private var instance: TvDataSource? = null

        fun getInstance(appExecutors: AppExecutors, dao: TvShowDao) =
            instance ?: synchronized(this) {
                instance?: TvDataSource(appExecutors, dao).also { instance = it }
            }
    }

    fun fetchDiscoverTv() {
        appExecutors.networkIO().execute {
            NetworkUtils.getDiscoverTvFromServer(object : DisposableObserver<DiscoverTv>() {
                override fun onComplete() {

                }

                override fun onNext(t: DiscoverTv) {
                    NetworkUtils.convertToTvShowList(t).forEach {
                        dao.addMovie(it)
                    }
                }

                override fun onError(e: Throwable) {

                }

            })
        }
    }
}