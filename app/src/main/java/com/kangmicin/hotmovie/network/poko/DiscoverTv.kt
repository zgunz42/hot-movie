package com.kangmicin.hotmovie.network.poko
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiscoverTv(
    val page: Int,
    @field:Json(name = "total_results")
    val totalResults: Int,
    @field:Json(name = "total_pages")
    val totalPages: Int,
    val results: List<Result>
) {
    data class Result(
        @field:Json(name = "original_name")
        val originalName: String,
        val name: String,
        val popularity: Double,
        @field:Json(name = "origin_country")
        val originCountry: List<String>,
        @field:Json(name = "vote_count")
        val voteCount: Int,
        @field:Json(name = "first_air_date")
        val firstAirDate: String,
        @field:Json(name = "backdrop_path")
        val backdropPath: String,
        @field:Json(name = "original_language")
        val originalLanguage: String,
        val id: Int,
        @field:Json(name = "vote_average")
        val voteAverage: Double,
        val overview: String,
        @field:Json(name = "poster_path")
        val posterPath: String
    )
}