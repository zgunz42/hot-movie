package com.kangmicin.hotmovie.network

import android.annotation.SuppressLint
import android.util.Log
import com.kangmicin.hotmovie.network.poko.DiscoverMovie
import com.kangmicin.hotmovie.data.Movie
import com.kangmicin.hotmovie.data.Tv
import com.kangmicin.hotmovie.network.poko.DiscoverTv
import com.kangmicin.hotmovie.utilities.Constant
import com.squareup.moshi.Moshi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*


object NetworkUtils {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }

    private fun getImageUrl(id: String, size: ImageSize): String {
        return Constant.ImageUrl + size.value + id
    }

    @SuppressLint("LongLogTag")
    fun getDiscoverMovieFromServer(observer: DisposableObserver<DiscoverMovie>): DisposableObserver<DiscoverMovie>? {
        return try {
            val apiService = getRetrofit().create(RestApi::class.java)
            val language = Locale.getDefault()
            val observable = apiService.getDiscoverMovie(Constant.apiKey, language.isO3Language)

            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)

        }catch (e: Exception) {
            Log.i("ThreadNetwork:getDiscoverMovieFromServer[Error]",  "" + e.localizedMessage)
            null
        }
    }

    fun getDiscoverTvFromServer(observer: DisposableObserver<DiscoverTv>): DisposableObserver<DiscoverTv>? {
        return try {
            val apiService = getRetrofit().create(RestApi::class.java)
            val language = Locale.getDefault()
            val observable = apiService.getDiscoverTv(Constant.apiKey, language.isO3Language)

            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)

        }catch (e: Exception) {
            null
        }
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
                val movie = Movie(result.id, result.title, posterUrl, result.overview)

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
                val tvShow = Tv(result.id, result.name, posterUrl, result.overview)

                tvShows.add(tvShow)
            }
        }catch (e: Exception) {
            Log.i("ThreadNetwork:convertToTvShowList[Error]",  "" + e.localizedMessage)
        }

        return tvShows
    }
}