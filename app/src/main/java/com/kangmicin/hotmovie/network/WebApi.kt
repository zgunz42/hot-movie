package com.kangmicin.hotmovie.network

import com.kangmicin.hotmovie.network.poko.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebApi {

    @GET("discover/movie")
    fun getDiscoverMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String): Single<DiscoverMovie>

    @GET("discover/tv")
    fun getDiscoverTv(
        @Query("api_key") apiKey: String,
        @Query("language") language: String): Single<DiscoverTv>

    @GET("movie/{id}/credits")
    fun getMovieCrew(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<Credits>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MovieDetail>

    @GET("tv/{id}")
    fun getTvDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<TvDetail>

    @GET("movie/{id}/credits")
    fun getTvCrew(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<Credits>
}