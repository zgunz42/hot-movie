package com.kangmicin.hotmovie.model

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.HashMap

@Parcelize
class TvShow(
    val id: String,
    val title: String,
    val poster: String,
    var plot: String,
    var genre: List<String>,
    var length: Long,
    var creators: List<Person>,
    var release: Date,
    var rating: List<Rating>,
    val actors: HashMap<Person, String>
) : Parcelable, ViewModel() {
    override fun toString(): String {
        return "TvShow($title)"
    }
}