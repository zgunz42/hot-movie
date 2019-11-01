package com.kangmicin.hotmovie.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    var id: Long = 0L,
    var name: String = "",
    var backdrop: String = "",
    var poster: String = "",
    var plot: String = "",
    var genre: List<String> = emptyList(),
    var length: Long = 0,
    var directors: List<Person> = emptyList(),
    var release: Date = Date(),
    var rating: List<Rating> = emptyList(),
    var actors: Map<String, Person> = emptyMap(),
    var language: String,
    var created: Date = Date(),
    var updated: Date = Date(),
    val isFavorite: Boolean = false
) : Parcelable, DisplayData {
    override fun getTitle() = name

    override fun getSubtitle() = plot

    override fun getThumbnail() = poster

    override fun getTag() = this

    override fun toString(): String {
        return "Movie($name)"
    }
}