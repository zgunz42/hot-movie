package com.kangmicin.hotmovie.utilities

object Helper {
    fun getTimeFormat(seconds: Long): Pair<Long, Long> {
        val hours = seconds / 3600
        val minutes = (seconds / 60) % 60

        return Pair(hours, minutes)
    }

}