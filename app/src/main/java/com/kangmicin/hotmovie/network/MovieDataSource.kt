package com.kangmicin.hotmovie.network

import android.util.Log
import com.kangmicin.hotmovie.data.Person
import com.kangmicin.hotmovie.network.poko.Credits
import com.kangmicin.hotmovie.network.poko.DiscoverMovie
import com.kangmicin.hotmovie.network.poko.MovieDetail
import com.kangmicin.hotmovie.storage.MovieDao
import com.kangmicin.hotmovie.utilities.AppExecutors
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
        dao.toggleFetch(true)
        appExecutors.networkIO().execute {
            NetworkUtils.getDiscoverMovieFromServer(object : DisposableObserver<DiscoverMovie>() {
                override fun onComplete() {
                    dao.toggleFetch(false)
                    dao.toggleError(false)
                }

                override fun onNext(t: DiscoverMovie) {
                    dao.clearMovies()
                    NetworkUtils.convertToMovieList(t).forEach {
                        dao.addMovie(it)
                    }
                }

                override fun onError(e: Throwable) {
                    dao.toggleFetch(false)
                    dao.toggleError(true)
                }

            })
        }
    }

    fun fetchMovieDetail(id: Int) {
        dao.toggleFetch(true)
        appExecutors.networkIO().execute {
            NetworkUtils.getMovieDetailFromServer("$id", object : DisposableObserver<MovieDetail>() {
                override fun onComplete() {
                    dao.toggleFetch(false)
                    dao.toggleError(false)
                    Log.i("ThreadNetwork", "complete")
                }

                override fun onNext(t: MovieDetail) {
                    val movies = dao.getMovies().value

                    if (movies != null) {
                        for (element in movies) {
                            if (element.id == id) {
                                element.length = t.runtime + 0L
                                t.genres.forEach { m ->
                                    element.genre = element.genre + m.name
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
            NetworkUtils.getMovieCrewFromServer("$id", object : DisposableObserver<Credits>() {
                override fun onComplete() {
                    dao.toggleFetch(false)
                    dao.toggleFetch(false)
                    Log.i("ThreadNetwork", "complete")
                }

                override fun onNext(t: Credits) {
                    val movies = dao.getMovies().value

                    if (movies != null) {
                        for (element in movies) {
                            if (element.id == id) {
                                element.actors.clear()
                                element.directors = emptyList()
                                t.crew.forEach {
                                    if (it.job == "Director") {
                                        val director = Person("" + it.id, it.name, null)
                                        element.directors = element.directors + director
                                    }
                                }
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