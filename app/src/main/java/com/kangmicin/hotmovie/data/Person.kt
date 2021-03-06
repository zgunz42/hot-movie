package com.kangmicin.hotmovie.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Person(
    val id: String,
    val name: String,
    val profileUrl: String?,
    val order: Int = 0
): Parcelable {
    override fun toString(): String {
        return "Person($name, $profileUrl)"
    }
}