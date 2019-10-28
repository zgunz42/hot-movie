package com.kangmicin.hotmovie.network

import android.util.Log
import com.kangmicin.hotmovie.data.Person
import com.kangmicin.hotmovie.network.poko.Credits
import com.kangmicin.hotmovie.network.poko.DiscoverTv
import com.kangmicin.hotmovie.network.poko.TvDetail
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
        dao.toggleFetch(true)
        appExecutors.networkIO().execute {
            NetworkUtils.getDiscoverTvFromServer(object : DisposableObserver<DiscoverTv>() {
                override fun onComplete() {
                    dao.toggleError(false)
                    dao.toggleFetch(false)
                }

                override fun onNext(t: DiscoverTv) {
                    dao.clearTvs()
                    NetworkUtils.convertToTvShowList(t).forEach {
                        dao.addTvShow(it)
                    }
                }

                override fun onError(e: Throwable) {
                    dao.toggleError(true)
                }

            })
        }
    }

    fun fetchTvDetail(id: Int) {
        dao.toggleFetch(true)
        appExecutors.networkIO().execute {
            NetworkUtils.getTvDetailFromServer("$id", object : DisposableObserver<TvDetail>() {
                override fun onComplete() {
                    dao.toggleFetch(false)
                    dao.toggleFetch(false)
                    Log.i("ThreadNetwork", "complete")
                }

                override fun onNext(t: TvDetail) {
                    val tvs = dao.getTvShows().value

                    if (tvs != null) {
                        for (element in tvs) {
                            if (element.id == id) {
                                element.length = t.episodeRunTime.first() + 0L
                                t.genres.forEach { m ->
                                    element.genre = element.genre + m.name
                                }
                                t.createdBy.forEach { c ->
                                    val creator = Person("" + c.id, c.name, null)
                                    element.creators = element.creators + creator
                                }
                            }

                        }
                    }

                }

                override fun onError(e: Throwable) {
                    dao.toggleFetch(false)
                    dao.toggleError(true)
                    Log.i("ThreadNetwork", "" + e.localizedMessage)
                }

            })
        }
        appExecutors.networkIO().execute  {
            NetworkUtils.getTvCrewFromServer("$id", object : DisposableObserver<Credits>() {
                override fun onComplete() {
                    dao.toggleFetch(false)
                    dao.toggleFetch(false)
                    Log.i("ThreadNetwork", "complete")
                }

                override fun onNext(t: Credits) {
                    val tvs = dao.getTvShows().value

                    if (tvs != null) {
                        for (element in tvs) {
                            if (element.id == id) {
                                element.actors.clear()
                                element.creators = emptyList()
                                t.cast.forEach {
                                    val profileUrl = NetworkUtils.getImageUrl(
                                        it.profilePath ?: "",
                                        ImageSize.Small
                                    )
                                    val actor = Person("" + it.id, it.name, profileUrl, it.order)

                                    element.actors[actor] = it.character
                                }
                            }

                        }
                    }

                }

                override fun onError(e: Throwable) {
                    dao.toggleFetch(false)
                    dao.toggleError(true)
                    Log.i("ThreadNetwork", "" + e.localizedMessage)
                }

            })
        }
    }
}