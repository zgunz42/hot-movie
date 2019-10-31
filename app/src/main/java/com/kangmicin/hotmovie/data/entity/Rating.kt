package com.kangmicin.hotmovie.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "rating")
class Rating (
    @PrimaryKey
    var id: Long = 0L,
    var amount: String,
    var source: String): Parcelable {
    override fun toString(): String {
        return "Rating($amount, $source)"
    }
}