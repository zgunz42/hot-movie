package com.kangmicin.hotmovie.network

import android.annotation.SuppressLint
import android.util.Log
import com.kangmicin.hotmovie.data.entity.Movie
import com.kangmicin.hotmovie.data.entity.Tv
import com.kangmicin.hotmovie.network.poko.*
import com.kangmicin.hotmovie.utilities.Constant
import com.kangmicin.hotmovie.utilities.Helper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

// TODO: mark to remove
class WebClient @Inject constructor(
    private val api: WebApi,
    private val retrofit: Retrofit) {

    fun getImageUrl(id: String, size: ImageSize): String {
        return Constant.ImageUrl + size.value + id
    }

    @SuppressLint("LongLogTag")
    fun <T> getFromServer(observer: DisposableSingleObserver<T>, observable: Single<T>): DisposableSingleObserver<T>? {
        return try {
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)

        }catch (e: Exception) {
            Log.i("ThreadNetwork:getDiscoverMovieFromServer[Error]",  "" + e.localizedMessage)
            null
        }
    }

    fun getDiscoverMovieFromServer(observer: DisposableSingleObserver<DiscoverMovie>): DisposableSingleObserver<DiscoverMovie>? {
        val observable = api.getDiscoverMovie(Constant.apiKey, Helper.languageParam())
        return getFromServer(observer, observable)
    }

    fun getDiscoverTvFromServer(observer: DisposableSingleObserver<DiscoverTv>): DisposableSingleObserver<DiscoverTv>? {
        val observable = api.getDiscoverTv(Constant.apiKey, Helper.languageParam())
        return getFromServer(observer, observable)
    }

    fun getTvDetailFromServer(id: String, observer: DisposableSingleObserver<TvDetail>): DisposableSingleObserver<TvDetail>? {
        val observable = api.getTvDetail(id, Constant.apiKey, Helper.languageParam())
        return getFromServer(observer, observable)
    }

    fun getMovieDetailFromServer(id: String, observer: DisposableSingleObserver<MovieDetail>): DisposableSingleObserver<MovieDetail>? {
        val observable = api.getMovieDetail(id, Constant.apiKey, Helper.languageParam())
        return getFromServer(observer, observable)
    }

    fun getTvCrewFromServer(id: String, observer: DisposableSingleObserver<Credits>): DisposableSingleObserver<Credits>? {
        
        val observable = api.getTvCrew(id, Constant.apiKey, Helper.languageParam())

        return getFromServer(observer, observable)
    }

    fun getMovieCrewFromServer(id: String, observer: DisposableSingleObserver<Credits>): DisposableSingleObserver<Credits>? {
        
        val observable = api.getMovieCrew(id, Constant.apiKey, Helper.languageParam())

        return getFromServer(observer, observable)
    }

    @SuppressLint("LongLogTag")
    fun convertToMovieList(response: DiscoverMovie): MutableList<Movie> {
        val movies = mutableListOf<Movie>()
        val results = response.results

        try {
            for (i in 0 until results.size) {
                val result = results[i]
                Log.i("ThreadNetwork", result.posterPath + ":" + result.title + ":" + result.backdropPath)
                val posterUrl = getImageUrl(result.posterPath, ImageSize.Small)
                val backdropUrl = getImageUrl(result.backdropPath, ImageSize.Medium)
                val movie = Movie(result.id + 0L, language = Helper.languageParam())
                movie.name = result.title
                movie.backdrop = backdropUrl
                movie.poster = posterUrl
                movie.plot = result.overview
                movie.release = Helper.intoDate(result.releaseDate)

                movies.add(movie)
            }
        }catch (e: Exception) {
            Log.i("ThreadNetwork:convertToMovieList[Error]",  "" + e.localizedMessage)
        }

        return movies
    }

    @SuppressLint("LongLogTag")
    fun convertToTvShowList(response: DiscoverTv): MutableList<Tv> {
        val tvShows = mutableListOf<Tv>()
        val results = response.results

        try {
            for (i in 0 until results.size) {
                val result = results[i]
                val posterUrl = getImageUrl(result.posterPath, ImageSize.Small)
                val tvShow = Tv(result.id + 0L, language = Helper.languageParam())
                val backdropUrl = getImageUrl(result.backdropPath, ImageSize.Medium)
                tvShow.name = result.name
                tvShow.backdrop = backdropUrl
                tvShow.poster = posterUrl
                tvShow.plot = result.overview
                tvShow.release = Helper.intoDate(result.firstAirDate)

                tvShows.add(tvShow)
            }
        }catch (e: Exception) {
            Log.i("ThreadNetwork:convertToTvShowList[Error]",  "" + e.localizedMessage)
        }

        return tvShows
    }
}