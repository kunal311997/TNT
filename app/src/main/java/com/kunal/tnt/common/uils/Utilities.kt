package com.kunal.tnt.common.uils

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.PermissionChecker
import coil.api.load
import com.kunal.tnt.R
import com.kunal.tnt.enroll.ui.LoginActivity
import com.kunal.tnt.enroll.ui.WalkThroughActivity
import com.kunal.tnt.home.ui.HomeActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.regex.Matcher
import java.util.regex.Pattern


object Utilities {


    private const val URL_REGEX =
        "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
                "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
                "([).!';/?:,][[:blank:]])?$"

    private val URL_PATTERN: Pattern = Pattern.compile(URL_REGEX)

    fun String?.isValidUrl(): Boolean {
        if (this == null) {
            return false
        }
        val matcher: Matcher = URL_PATTERN.matcher(this)
        return matcher.matches()
    }


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

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.gone() {
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

    fun checkGalleryPermissions(context: Context): Boolean {
        if (
            PermissionChecker.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_DENIED ||
            PermissionChecker.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_DENIED

        ) {
            return false
        }
        return true
    }

    fun checkCameraPermissions(context: Context): Boolean {
        if (PermissionChecker.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            )
            == PermissionChecker.PERMISSION_DENIED ||
            PermissionChecker.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            == PermissionChecker.PERMISSION_DENIED
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

    fun loadJSONFromAsset(context: Context, fileName: String): String? {
        var json: String?
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun Bitmap.convertBitmapTobase64(): String {
        val baos = ByteArrayOutputStream()
        val bitmap = Bitmap.createScaledBitmap(this, this.width, this.height, false)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes: ByteArray = baos.toByteArray()
        val encoded: String = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
        return encoded
    }

    fun showChooserForLinkShare(context: Context, message: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            context.getString(R.string.app_name) + " " + context.getString(R.string.share)
        )
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        context.startActivity(
            Intent.createChooser(
                shareIntent,
                context.getString(R.string.share)
            )
        )
    }

    fun processEnrollFlow(context: Context, prefClient: SharedPrefClient) {

        when {

            !prefClient.isUserLoggedIn() -> {
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }

            !prefClient.isWalkThroughDone() -> {
                val intent = Intent(context, WalkThroughActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }

            else -> {
                val intent = Intent(context, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }

    fun Context.openWebPage(url: String?) {
        try {
            val webpage = Uri.parse(url)
            val myIntent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No application can handle this request. Please install a web browser or check your URL.",
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
    }
}