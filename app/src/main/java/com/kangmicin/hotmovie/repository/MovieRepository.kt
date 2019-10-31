package com.kangmicin.hotmovie.repository

import com.kangmicin.hotmovie.data.core.MovieDAO
import com.kangmicin.hotmovie.data.entity.Movie
import com.kangmicin.hotmovie.network.WebClient
import com.kangmicin.hotmovie.network.poko.DiscoverMovie
import com.kangmicin.hotmovie.network.poko.MovieDetail
import com.kangmicin.hotmovie.utilities.AppExecutors
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieRepository @Inject constructor(
    executors: AppExecutors,
    private val dao: MovieDAO,
    private val webClient: WebClient
): Repository<Movie, DiscoverMovie, MovieDetail>(executors){
    override fun fetchLocale() = dao.getAll()

    override fun fetchItemSource(observer: DisposableSingleObserver<DiscoverMovie>) {
        webClient.getDiscoverMovieFromServer(observer)
    }

    override fun onDataFetched(target: List<Movie>) {
        dao.insertMovies(*target.toTypedArray())
    }

    override fun fromSource(to: DiscoverMovie): MutableList<Movie> {
        return webClient.convertToMovieList(to)
    }

    override fun fromDetail(from: Movie, to: MovieDetail) {}
}