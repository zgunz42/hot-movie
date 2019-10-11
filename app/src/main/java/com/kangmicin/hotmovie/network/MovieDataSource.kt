package com.kangmicin.hotmovie.network

import android.util.Log
import com.kangmicin.hotmovie.network.poko.DiscoverMovie
import com.kangmicin.hotmovie.utilities.AppExecutors
import com.kangmicin.hotmovie.storage.MovieDao
import io.reactivex.observers.DisposableObserver


class MovieDataSource private constructor(
    private val appExecutors: AppExecutors,
    private val dao: MovieDao
){
    companion object {
        @Volatile private var instance: MovieDataSource? = null

        fun getInstance(appExecutors: AppExecutors, dao: MovieDao) =
            instance ?: synchronized(this) {
                instance?: MovieDataSource(appExecutors, dao).also { instance = it }
            }
    }

    fun fetchDiscoverMovie() {
        dao.clearMovies()
        appExecutors.networkIO().execute {
            NetworkUtils.getDiscoverMovieFromServer(object : DisposableObserver<DiscoverMovie>() {
                override fun onComplete() {
                    Log.i("ThreadNetwork", "complete")
                }

                override fun onNext(t: DiscoverMovie) {
                    Log.i("ThreadNetwork", "complete" + t.page)
                    NetworkUtils.convertToMovieList(t).forEach {
                        dao.addMovie(it)
                    }
                }

                override fun onError(e: Throwable) {

                }

            })
        }
    }
}