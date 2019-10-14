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
        dao.clearMovies()
        dao.toggleFetch(true)
        appExecutors.networkIO().execute {
            NetworkUtils.getDiscoverMovieFromServer(object : DisposableObserver<DiscoverMovie>() {
                override fun onComplete() {
                    dao.toggleFetch(false)
                    dao.toggleError(false)
                }

                override fun onNext(t: DiscoverMovie) {
                    Log.i("ThreadNetwork", "complete" + t.page)
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
                        for (i in 0 until movies.size) {
                            val movie = movies[i]
                            if (movie.id == id) {
                                movie.length = t.runtime + 0L
                                t.genres.forEach { m ->
                                    movie.genre = movie.genre + m.name
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
                        for (i in 0 until movies.size) {
                            val movie = movies[i]
                            if (movie.id == id) {
                                movie.actors.clear()
                                movie.directors = emptyList()
                                t.crew.forEach {
                                    if (it.job == "Director") {
                                        val director = Person("" + it.id, it.name, null)
                                        movie.directors = movie.directors + director
                                    }
                                }
                                t.cast.forEach {
                                    val profileUrl = NetworkUtils.getImageUrl(
                                        it.profilePath ?: "",
                                        ImageSize.Small
                                    )
                                    val actor = Person("" + it.id, it.name, profileUrl, it.order)

                                    movie.actors[actor] = it.character
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