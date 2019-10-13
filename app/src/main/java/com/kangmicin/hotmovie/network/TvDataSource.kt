package com.kangmicin.hotmovie.network

import android.util.Log
import com.kangmicin.hotmovie.data.Person
import com.kangmicin.hotmovie.network.poko.*
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
        dao.clearMovies()
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

    fun fetchTvDetail(id: Int) {
        appExecutors.networkIO().execute {
            NetworkUtils.getTvDetailFromServer("$id", object : DisposableObserver<TvDetail>() {
                override fun onComplete() {
                    Log.i("ThreadNetwork", "complete")
                }

                override fun onNext(t: TvDetail) {
                    val tvs = dao.getTvShows().value

                    if (tvs != null) {
                        for (i in 0 until tvs.size) {
                            val tv = tvs[i]
                            if (tv.id == id) {
                                tv.length = t.episodeRunTime.first() + 0L
                                t.genres.forEach { m ->
                                    tv.genre = tv.genre + m.name
                                }
                                t.createdBy.forEach { c ->
                                    val creator = Person("" + c.id, c.name, null)
                                    tv.creators = tv.creators + creator
                                }
                            }

                        }
                    }

                }

                override fun onError(e: Throwable) {
                    Log.i("ThreadNetwork", "" + e.localizedMessage)
                }

            })
        }
        appExecutors.networkIO().execute  {
            NetworkUtils.getTvCrewFromServer("$id", object : DisposableObserver<Credits>() {
                override fun onComplete() {
                    Log.i("ThreadNetwork", "complete")
                }

                override fun onNext(t: Credits) {
                    val movies = dao.getTvShows().value

                    if (movies != null) {
                        for (i in 0 until movies.size) {
                            val movie = movies[i]
                            if (movie.id == id) {
                                t.cast.forEach {
                                    val profileUrl = NetworkUtils.getImageUrl(
                                        it.profilePath ?: "",
                                        ImageSize.Small
                                    )
                                    val actor = Person("" + it.id, it.name, profileUrl)

                                    movie.actors[actor] = it.character
                                }
                            }

                        }
                    }

                }

                override fun onError(e: Throwable) {
                    Log.i("ThreadNetwork", "" + e.localizedMessage)
                }

            })
        }
    }
}