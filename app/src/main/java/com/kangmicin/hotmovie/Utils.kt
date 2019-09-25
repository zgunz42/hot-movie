package com.kangmicin.hotmovie

import android.content.Context

object Utils {
    fun getIdentity(context: Context, path: String, type: ResType): Int {
        val fileName = path.split('/').last()
        val name = fileName.split('.').first()
        val resources = context.resources

        return resources.getIdentifier(name, "drawable", context.packageName)
    }

    enum class ResType(name: String) {
        DRAWABLE("drawable")
    }
}