package com.kangmicin.hotmovie.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.annotation.DimenRes
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory

object Ui {
    @Suppress("SameParameterValue")
    private fun getIdentity(context: Context, path: String, type: ResType): Int {
        val fileName = path.split('/').last()
        val name = fileName.split('.').first()
        val resources = context.resources

        return resources.getIdentifier(name, type.value, context.packageName)
    }

    fun roundedImage(context: Context, name: String, @DimenRes corner: Int): RoundedBitmapDrawable {
        val cornerRadius = context.resources.getDimension(corner)
        return roundedImage(context, name, cornerRadius)
    }

    private fun roundedImage(context: Context, name: String, corner: Float): RoundedBitmapDrawable {
        val identifier = getIdentity(context, name, ResType.DRAWABLE)
        val bitmap = BitmapFactory.decodeResource(context.resources, identifier)
        val rImage = RoundedBitmapDrawableFactory.create(context.resources, bitmap)

        rImage.cornerRadius = corner

        return rImage
    }

    fun updateActivity(activity: Activity) {
        val intent = activity.intent

        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_ANIMATION)
        activity.overridePendingTransition(0, 0)
        activity.finish()
        activity.overridePendingTransition(0, 0)
        activity.startActivity(intent)
    }

    enum class ResType(val value: String) {
        DRAWABLE("drawable"),
    }
}