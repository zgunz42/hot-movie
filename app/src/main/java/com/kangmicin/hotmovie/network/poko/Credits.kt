package com.kangmicin.hotmovie.network.poko


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Credits(
    val id: Int,
    val cast: List<Actor>,
    val crew: List<Crew>
) {
    data class Crew(
        @field:Json(name = "credit_id")
        val creditId: String,
        val department: String,
        val gender: Int,
        val id: Int,
        val job: String,
        val name: String,
        @field:Json(name = "profile_path")
        val profilePath: String?
    )

    data class Actor(
        @field:Json(name = "credit_id")
        val creditId: String,
        val id: Int,
        val character: String,
        val name: String,
        @field:Json(name = "profile_path")
        val profilePath: String?
    )
}