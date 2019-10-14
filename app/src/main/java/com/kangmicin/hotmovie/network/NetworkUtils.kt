package com.kangmicin.hotmovie.network

import android.annotation.SuppressLint
import android.util.Log
import com.kangmicin.hotmovie.data.Movie
import com.kangmicin.hotmovie.data.Tv
import com.kangmicin.hotmovie.network.poko.*
import com.kangmicin.hotmovie.utilities.Constant
import com.kangmicin.hotmovie.utilities.Helper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.logging.HttpLoggingInterceptor




object NetworkUtils {

    private fun getRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

    }

    fun getImageUrl(id: String, size: ImageSize): String {
        return Constant.ImageUrl + size.value + id
    }

    @SuppressLint("LongLogTag")
    fun <T> getFromServer(observer: DisposableObserver<T>, observable: Observable<T>): DisposableObserver<T>? {
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

    fun getDiscoverMovieFromServer(observer: DisposableObserver<DiscoverMovie>): DisposableObserver<DiscoverMovie>? {
        val apiService = getRetrofit().create(RestApi::class.java)
        val observable = apiService.getDiscoverMovie(Constant.apiKey, Helper.languageParam())

        return getFromServer(observer, observable)
    }

    fun getDiscoverTvFromServer(observer: DisposableObserver<DiscoverTv>): DisposableObserver<DiscoverTv>? {
        val apiService = getRetrofit().create(RestApi::class.java)
        val observable = apiService.getDiscoverTv(Constant.apiKey, Helper.languageParam())

        return getFromServer(observer, observable)
    }

    fun getTvDetailFromServer(id: String, observer: DisposableObserver<TvDetail>): DisposableObserver<TvDetail>? {
        val apiService = getRetrofit().create(RestApi::class.java)
        val observable = apiService.getTvDetail(id, Constant.apiKey, Helper.languageParam())

        return getFromServer(observer, observable)
    }

    fun getMovieDetailFromServer(id: String, observer: DisposableObserver<MovieDetail>): DisposableObserver<MovieDetail>? {
        val apiService = getRetrofit().create(RestApi::class.java)
        val observable = apiService.getMovieDetail(id, Constant.apiKey, Helper.languageParam())

        return getFromServer(observer, observable)
    }

    fun getTvCrewFromServer(id: String, observer: DisposableObserver<Credits>): DisposableObserver<Credits>? {
        val apiService = getRetrofit().create(RestApi::class.java)
        val observable = apiService.getTvCrew(id, Constant.apiKey, Helper.languageParam())

        return getFromServer(observer, observable)
    }

    fun getMovieCrewFromServer(id: String, observer: DisposableObserver<Credits>): DisposableObserver<Credits>? {
        val apiService = getRetrofit().create(RestApi::class.java)
        val observable = apiService.getMovieCrew(id, Constant.apiKey, Helper.languageParam())

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
                val movie = Movie(result.id)
                movie.title = result.title
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
                val tvShow = Tv(result.id)
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