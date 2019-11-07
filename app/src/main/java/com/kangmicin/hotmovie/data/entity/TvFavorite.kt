package com.kangmicin.hotmovie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = Tv::class,
    childColumns = ["movie_id"],
    parentColumns = ["id"]
)])
data class TvFavorite(
    @PrimaryKey
    var id: Long = 0L,
    @ColumnInfo(name = "movie_id") var movieId: Long,
    var isFavorite: Boolean
)