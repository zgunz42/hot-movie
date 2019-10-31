package com.kangmicin.hotmovie.data.entity

interface DisplayData {
    fun getTitle(): String
    fun getSubtitle(): String
    fun getThumbnail(): String
    fun getTag(): Any
}