package com.kangmicin.hotmovie.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "person")
class Person(
    @PrimaryKey
    var id: Long = 0L,
    val name: String,
    val profileUrl: String?,
    val order: Int = 0
): Parcelable {
    override fun toString(): String {
        return "Person($name, $profileUrl)"
    }
}