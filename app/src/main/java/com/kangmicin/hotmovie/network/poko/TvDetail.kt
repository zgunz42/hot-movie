package com.kangmicin.hotmovie.network.poko


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvDetail(
    @field:Json(name = "backdrop_path")
    val backdropPath: String,
    @field:Json(name = "created_by")
    val createdBy: List<CreatedBy>,
    @field:Json(name = "episode_run_time")
    val episodeRunTime: List<Int>,
    @field:Json(name = "first_air_date")
    val firstAirDate: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @field:Json(name = "in_production")
    val inProduction: Boolean,
    val languages: List<String>,
    @field:Json(name = "last_air_date")
    val lastAirDate: String,
    @field:Json(name = "last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir,
    val name: String,
    @field:Json(name = "next_episode_to_air")
    val nextEpisodeToAir: NextEpisodeToAir,
    val networks: List<Network>,
    @field:Json(name = "number_of_episodes")
    val numberOfEpisodes: Int,
    @field:Json(name = "number_of_seasons")
    val numberOfSeasons: Int,
    @field:Json(name = "origin_country")
    val originCountry: List<String>,
    @field:Json(name = "original_language")
    val originalLanguage: String,
    @field:Json(name = "original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @field:Json(name = "poster_path")
    val posterPath: String,
    @field:Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,
    val seasons: List<Season>,
    val status: String,
    val type: String,
    @field:Json(name = "vote_average")
    val voteAverage: Double,
    @field:Json(name = "vote_count")
    val voteCount: Int
) {
    data class CreatedBy(
        val id: Int,
        @field:Json(name = "credit_id")
        val creditId: String,
        val name: String,
        val gender: Int,
        @field:Json(name = "profile_path")
        val profilePath: String
    )

    data class Genre(
        val id: Int,
        val name: String
    )

    data class LastEpisodeToAir(
        @field:Json(name = "air_date")
        val airDate: String,
        @field:Json(name = "episode_number")
        val episodeNumber: Int,
        val id: Int,
        val name: String,
        val overview: String,
        @field:Json(name = "production_code")
        val productionCode: String,
        @field:Json(name = "season_number")
        val seasonNumber: Int,
        @field:Json(name = "show_id")
        val showId: Int,
        @field:Json(name = "still_path")
        val stillPath: String,
        @field:Json(name = "vote_average")
        val voteAverage: Int,
        @field:Json(name = "vote_count")
        val voteCount: Int
    )

    data class NextEpisodeToAir(
        @field:Json(name = "air_date")
        val airDate: String,
        @field:Json(name = "episode_number")
        val episodeNumber: Int,
        val id: Int,
        val name: String,
        val overview: String,
        @field:Json(name = "production_code")
        val productionCode: String,
        @field:Json(name = "season_number")
        val seasonNumber: Int,
        @field:Json(name = "show_id")
        val showId: Int,
        @field:Json(name = "still_path")
        val stillPath: String,
        @field:Json(name = "vote_average")
        val voteAverage: Int,
        @field:Json(name = "vote_count")
        val voteCount: Int
    )

    data class Network(
        val name: String,
        val id: Int,
        @field:Json(name = "logo_path")
        val logoPath: String,
        @field:Json(name = "origin_country")
        val originCountry: String
    )

    data class ProductionCompany(
        val id: Int,
        @field:Json(name = "logo_path")
        val logoPath: String?,
        val name: String,
        @field:Json(name = "origin_country")
        val originCountry: String
    )

    data class Season(
        @field:Json(name = "air_date")
        val airDate: String,
        @field:Json(name = "episode_count")
        val episodeCount: Int,
        val id: Int,
        val name: String,
        val overview: String,
        @field:Json(name = "poster_path")
        val posterPath: String,
        @field:Json(name = "season_number")
        val seasonNumber: Int
    )
}