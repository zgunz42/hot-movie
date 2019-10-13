package com.kangmicin.hotmovie.repository

import com.kangmicin.hotmovie.network.MovieDataSource
import com.kangmicin.hotmovie.storage.MovieDao
import com.kangmicin.hotmovie.network.poko.DiscoverMovie
import com.kangmicin.hotmovie.utilities.AppExecutors
import java.util.*

class MovieRepository private constructor(
    private val appExecutors: AppExecutors,
    private val dao: MovieDao
){
    val service = MovieDataSource.getInstance(appExecutors, dao)

    companion object {
        private const val ENDPOINT = "/discover/movie"

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

    fun getMovies() = dao.getMovies()

    fun loadServiceMovie(id: Int) {
        service.fetchMovieDetail(id)
    }
}