package com.kangmicin.hotmovie

import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DimenRes
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory

object Utils {
    fun getIdentity(context: Context, path: String, type: ResType): Int {
        val fileName = path.split('/').last()
        val name = fileName.split('.').first()
        val resources = context.resources

        return resources.getIdentifier(name, "drawable", context.packageName)
    }

    fun getTimeFormat(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds / 60) % 60

        return "${hours}h ${minutes}m"
    }

    fun roundedImage(context: Context, name: String, @DimenRes corner: Int): RoundedBitmapDrawable {
        val cornerRadius = context.resources.getDimension(corner)
        return roundedImage(context, name, cornerRadius)
    }

    fun roundedImage(context: Context, name: String, corner: Float): RoundedBitmapDrawable {
        val identifier = getIdentity(context, name, Utils.ResType.DRAWABLE)
        val bitmap = BitmapFactory.decodeResource(context.resources, identifier)
        val rImage = RoundedBitmapDrawableFactory.create(context.resources, bitmap)

        rImage.cornerRadius = context.resources.getDimension(R.dimen.corner)

        return rImage
    }

    enum class ResType(name: String) {
        DRAWABLE("drawable")
    }
}