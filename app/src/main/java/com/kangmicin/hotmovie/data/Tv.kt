package com.kangmicin.hotmovie.data

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.HashMap

@Parcelize
data class Tv(
    var id: Int,
    var title: String,
    var poster: String,
    var plot: String,
    var genre: List<String> = emptyList(),
    var length: Long = 0,
    var creators: List<Person> = emptyList(),
    var release: Date = Date(),
    var actors: HashMap<Person, String> = HashMap()
) : Parcelable {
    override fun toString(): String {
        return "Tv($title)"
    }
}