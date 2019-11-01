package com.kangmicin.hotmovie.utilities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

object BindingAdapters {
    /**
     * Binding Adapters
     */
    @JvmStatic
    @BindingAdapter("glideImage")
    fun loadImage(view: ImageView, imageUrl: String) {

        val placeholder = view.drawable ?: ColorDrawable(Color.GRAY)
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(placeholder)
            .error(placeholder)
            .apply(
                RequestOptions
                    .centerCropTransform()
                    .transform(RoundedCorners(10))
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}