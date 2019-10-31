package com.kangmicin.hotmovie.utilities

import androidx.room.TypeConverter
import com.kangmicin.hotmovie.data.entity.Person
import com.kangmicin.hotmovie.data.entity.Rating
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.*


object Converters {
    @TypeConverter
    @JvmStatic
    fun fromList(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    @JvmStatic
    fun stringtoList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    @JvmStatic
    fun toTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    @JvmStatic
    fun toPersons(value: String): List<Person>? {
        val type = Types.newParameterizedType(List::class.java, Person::class.java)
        return Moshi.Builder().build().adapter<List<Person>>(type).fromJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromPersons(value: List<Person>): String? {
        val type = Types.newParameterizedType(List::class.java, Person::class.java)
        return Moshi.Builder().build().adapter<List<Person>>(type).toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun toRatings(value: String): List<Rating>? {
        val type = Types.newParameterizedType(List::class.java, Rating::class.java)
        return Moshi.Builder().build().adapter<List<Rating>>(type).fromJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromRatings(value: List<Rating>): String? {
        val type = Types.newParameterizedType(List::class.java, Rating::class.java)
        return Moshi.Builder().build().adapter<List<Rating>>(type).toJson(value)
    }


    @TypeConverter
    @JvmStatic
    fun toMap(value: String): Map<Person, String>? {
        val type = Types.newParameterizedType(Map::class.java, Person::class.java, String::class.java)
        return Moshi.Builder().build().adapter<Map<Person, String>>(type).fromJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromMap(value: Map<Person, String>): String? {
        val type = Types.newParameterizedType(Map::class.java, Person::class.java, String::class.java)
        return Moshi.Builder().build().adapter<Map<Person, String>>(type).toJson(value)
    }
}