package com.kangmicin.hotmovie.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.HashMap

@Parcelize
data class Movie(
    var id: Int,
    var title: String = "",
    var backdrop: String = "",
    var poster: String = "",
    var plot: String = "",
    var genre: List<String> = emptyList(),
    var length: Long = 0,
    var directors: List<Person> = emptyList(),
    var release: Date = Date(),
    var rating: List<Rating> = emptyList(),
    var actors: HashMap<Person, String> = HashMap()
) : Parcelable {

    override fun toString(): String {
        return "Movie($title)"
    }
}