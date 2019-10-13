package com.kangmicin.hotmovie.network.poko


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetail(
    val adult: Boolean,
    @field:Json(name = "backdrop_path")
    val backdropPath: String,
    @field:Json(name = "belongs_to_collection")
    val belongsToCollection: Any?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @field:Json(name = "imdb_id")
    val imdbId: String,
    @field:Json(name = "original_language")
    val originalLanguage: String,
    @field:Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @field:Json(name = "poster_path")
    val posterPath: String,
    @field:Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,
    @field:Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,
    @field:Json(name = "release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    @field:Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @field:Json(name = "vote_average")
    val voteAverage: Double,
    @field:Json(name = "vote_count")
    val voteCount: Int
) {
    data class Genre(
        val id: Int,
        val name: String
    )

    data class ProductionCompany(
        val id: Int,
        @field:Json(name = "logo_path")
        val logoPath: String?,
        val name: String,
        @field:Json(name = "origin_country")
        val originCountry: String
    )

    data class ProductionCountry(
        @field:Json(name = "iso_3166_1")
        val iso31661: String,
        val name: String
    )

    data class SpokenLanguage(
        @field:Json(name = "iso_639_1")
        val iso6391: String,
        val name: String
    )
}