package com.kangmicin.hotmovie.repository

import com.kangmicin.hotmovie.network.MovieDataSource
import com.kangmicin.hotmovie.storage.MovieDao
import com.kangmicin.hotmovie.utilities.AppExecutors

class MovieRepository private constructor(
    appExecutors: AppExecutors,
    private val dao: MovieDao
){
    private val service = MovieDataSource.getInstance(appExecutors, dao)

    companion object {

        @Volatile private var instance: MovieRepository? = null

        fun getInstance(appExecutors: AppExecutors, dao: MovieDao) =
            instance ?: synchronized(this) {
                instance
                    ?: MovieRepository(appExecutors, dao).also { instance = it }
            }
    }

    fun loadServiceMovies() {
        service.fetchDiscoverMovie()
    }

    fun fetchEvent() = dao.getFetchEvent()

    fun getMovies() = dao.getMovies()

    fun loadServiceMovie(id: Int) {
        service.fetchMovieDetail(id)
    }
}