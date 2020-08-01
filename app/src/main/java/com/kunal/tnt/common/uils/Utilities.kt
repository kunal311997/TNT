package com.kunal.tnt.common.uils

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import coil.api.load
import com.kunal.tnt.R


object Utilities {

    fun ImageView.loadImage(url: String) {
        this.load(url) {
            crossfade(true)
            placeholder(R.mipmap.ic_launcher)
        }
    }

    fun ProgressBar.showProgressbar() {
        this.visibility = View.VISIBLE
    }

    fun ProgressBar.hideProgressBar() {
        this.visibility = View.GONE
    }


    fun getThumbnailUrl(url: String): String {
        var thumbnailUrl = ""
        if (url.contains("youtube")) {
            val uri: Uri = Uri.parse(url)
            val videoId: String? = uri.getQueryParameter("v")
            thumbnailUrl = "https://img.youtube.com/vi/$videoId/mqdefault.jpg"
        }
        return thumbnailUrl
    }
}