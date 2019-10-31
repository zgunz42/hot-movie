package com.kangmicin.hotmovie.utilities

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
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

    fun hasInternetConnection(): Single<Boolean> {
        return Single.fromCallable {
            try {
                val timeoutMs = 1500
                val socket = Socket()
                val socketAddress = InetSocketAddress("api.themoviedb.org", 443)

                socket.connect(socketAddress, timeoutMs)
                socket.close()
                true
            } catch (e: IOException) {
                false
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}