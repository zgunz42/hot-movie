package com.kangmicin.hotmovie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = Movie::class,
    childColumns = ["movie_id"],
    parentColumns = ["id"]
)])
data class MovieFavorite(
    @PrimaryKey
    var id: Long = 0L,
    @ColumnInfo(name = "movie_id") var movieId: Long,
    var isFavorite: Boolean
)