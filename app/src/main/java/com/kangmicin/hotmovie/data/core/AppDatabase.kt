package com.kangmicin.hotmovie.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kangmicin.hotmovie.data.entity.Movie
import com.kangmicin.hotmovie.data.entity.Person
import com.kangmicin.hotmovie.data.entity.Rating
import com.kangmicin.hotmovie.data.entity.Tv
import com.kangmicin.hotmovie.utilities.Converters

@Database(entities = [Movie::class, Person::class, Rating::class, Tv::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
    abstract fun tvDAO(): TvDAO
}