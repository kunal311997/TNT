package com.kunal.tnt.common.uils

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import coil.api.load
import com.kunal.tnt.R
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


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

    fun getTextPart(str: String): RequestBody {
        return str.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun getImagePart(file: File, str: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            str,
            file?.name,
            file?.asRequestBody("image/*".toMediaTypeOrNull())
        )
    }
}