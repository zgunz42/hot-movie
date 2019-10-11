package com.kangmicin.hotmovie.network

import com.kangmicin.hotmovie.network.poko.DiscoverMovie
import com.kangmicin.hotmovie.network.poko.DiscoverTv
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("discover/movie")
    fun getDiscoverMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String): Observable<DiscoverMovie>

    @GET("discover/tv")
    fun getDiscoverTv(
        @Query("api_key") apiKey: String,
        @Query("language") language: String): Observable<DiscoverTv>
}