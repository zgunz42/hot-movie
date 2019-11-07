package com.kangmicin.hotmovie.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kangmicin.hotmovie.data.entity.*
import com.kangmicin.hotmovie.utilities.Converters

@Database(entities = [Movie::class, Person::class, Rating::class, Tv::class, TvFavorite::class, MovieFavorite::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
    abstract fun tvDAO(): TvDAO
}