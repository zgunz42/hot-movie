package com.kangmicin.hotmovie.utilities

import java.text.SimpleDateFormat
import java.util.*

object Helper {

    fun getTimeFormat(minute: Long): Pair<Long, Long> {
        val hours = minute / 60
        val minutes = minute % 60

        return Pair(hours, minutes)
    }

    fun intoDate(string: String): Date {
        val formatter = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
        val result = formatter.parse(string)

        if (result != null) {
            return result
        }

        throw IllegalArgumentException("invalid $string")
    }

    fun languageParam(locale: Locale = Locale.getDefault()): String {
        val language = locale.language
        val country = locale.country

        if (language == "in" && country == "ID") {
            return "id-ID"
        }

        return "$language-$country"
    }
}