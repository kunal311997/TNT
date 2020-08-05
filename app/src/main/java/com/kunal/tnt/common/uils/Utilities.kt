package com.kunal.tnt.common.uils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.fragment.app.FragmentActivity
import coil.api.load
import com.kunal.tnt.R
import com.kunal.tnt.enroll.ui.SignUpActivity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat


object Utilities {


    fun String.isValidEmail(): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(this).matches()
    }

    fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

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
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )
    }

    fun checkGalleryPermissions(context: FragmentActivity): Boolean {
        if (PermissionChecker.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_DENIED
        ) {
            return false
        }
        return true
    }

    @SuppressLint("SimpleDateFormat")
    fun String.formatDate(): String {
        val formatter = SimpleDateFormat(Constant.SERVER_FORMAT)
        val date = formatter.parse(this)
        return SimpleDateFormat(Constant.DATE_FORMAT).format(date)
    }

}