package com.kangmicin.hotmovie.network.poko


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiscoverMovie(
    val page: Int,
    @field:Json(name = "total_results")
    val totalResults: Int,
    @field:Json(name = "total_pages")
    val totalPages: Int,
    val results: List<Result>
) {
    data class Result(
        val popularity: Double,
        @field:Json(name = "vote_count")
        val voteCount: Int,
        val video: Boolean,
        @field:Json(name = "poster_path")
        val posterPath: String,
        val id: Int,
        val adult: Boolean,
        @field:Json(name = "backdrop_path")
        val backdropPath: String,
        @field:Json(name = "original_language")
        val originalLanguage: String,
        @field:Json(name = "original_title")
        val originalTitle: String,
        val title: String,
        @field:Json(name = "vote_average")
        val voteAverage: Double,
        val overview: String,
        @field:Json(name = "release_date")
        val releaseDate: String
    )
}