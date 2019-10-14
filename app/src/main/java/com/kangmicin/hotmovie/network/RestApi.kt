package com.kangmicin.hotmovie.network

import com.kangmicin.hotmovie.network.poko.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{id}/credits")
    fun getMovieCrew(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<Credits>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<MovieDetail>

    @GET("tv/{id}")
    fun getTvDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<TvDetail>

    @GET("movie/{id}/credits")
    fun getTvCrew(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<Credits>
}