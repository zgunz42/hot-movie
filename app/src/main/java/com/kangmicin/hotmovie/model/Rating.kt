package com.kangmicin.hotmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rating (
    val id: String,
    val amount: String,
    var source: String): Parcelable {
    override fun toString(): String {
        return "Rating($amount, $source)"
    }
}