package com.kunal.tnt.common.uils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.kunal.tnt.common.uils.Utilities.getThumbnailUrl
import com.kunal.tnt.common.uils.Utilities.loadImage

object BindingAdapters {

    @BindingAdapter("app:image")
    @JvmStatic
    fun setImage(imageView: ImageView, url: String) {
        imageView.loadImage(url)
    }

    @BindingAdapter("app:thumbnail")
    @JvmStatic
    fun setThumbnail(imageView: ImageView, url: String) {
        imageView.loadImage(getThumbnailUrl(url))
    }
}