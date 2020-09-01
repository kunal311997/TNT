package com.kunal.tnt.createfeed.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.flexbox.FlexboxLayoutManager
import com.kunal.tnt.R
import com.kunal.tnt.categories.Categories
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.SharedPrefClient
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.common.uils.Utilities.convertBitmapTobase64
import com.kunal.tnt.common.uils.Utilities.gone
import com.kunal.tnt.common.uils.Utilities.isValidUrl
import com.kunal.tnt.common.uils.Utilities.showToast
import com.kunal.tnt.common.uils.Utilities.visible
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.createfeed.adapter.KeywordsAdapter
import com.kunal.tnt.createfeed.utils.FeedConstants
import com.kunal.tnt.createfeed.viewmodel.CreateFeedViewModel
import com.kunal.tnt.databinding.ActivityCreateFeedBinding
import com.kunal.tnt.home.utils.HomeConstants
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_create_feed.*
import kotlinx.android.synthetic.main.layout_error_page.view.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject


class CreateFeedActivity : DaggerAppCompatActivity(), View.OnClickListener {

    private var file: File? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvidersFactory

    @Inject
    lateinit var adapter: KeywordsAdapter

    @Inject
    lateinit var preference: SharedPrefClient

    lateinit var binding: ActivityCreateFeedBinding

    private val viewModel: CreateFeedViewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[CreateFeedViewModel::class.java]
    }

    var title = ""

    var description = ""
    var source = ""
    var category = ""
    var cameraOutPutFileUri: Uri? = null

    var bitmap: Bitmap? = null

    private val keywordsList = ArrayList<Categories>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_feed)

        addOnclickListeners()
        setObservers()
        viewModel.getCategories()
        setName()
    }

    private fun setName() {
        txtName.text = resources.getString(R.string.by_name, preference.getUsername())
    }

    private fun setAdapter() {
        val staggeredGridLayoutManager = FlexboxLayoutManager(this)
        binding.rvKeywords.layoutManager = staggeredGridLayoutManager
        binding.rvKeywords.adapter = adapter
        adapter.addItems(keywordsList)
    }

    private fun addOnclickListeners() {
        imgGallery.setOnClickListener(this)
        imgCamera.setOnClickListener(this)
        btnDone.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {

            imgGallery -> {
                if (Utilities.checkGalleryPermissions(this)) {
                    val permissions = arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(
                        permissions,
                        FeedConstants.GALLERY_PERMISSION_CODE
                    )
                } else {
                    choosePhotoFromGallery()
                }
            }

            imgCamera -> {
                if (Utilities.checkCameraPermissions(this)) {
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(
                        permission,
                        FeedConstants.CAMERA_PERMISSION_CODE
                    )
                } else {
                    takePhotoFromCamera()
                }
            }

            btnDone -> {
                title = edtTitle.text.toString()
                description = edtDesc.text.toString()
                source = edtSource.text.toString()
                if (adapter.selectedPosition != -1) {
                    category = keywordsList[adapter.selectedPosition].categoryName
                }

                if (checkInputValidations(title, description, category, source)) {
                    if (bitmap != null)
                        uploadImageToImgur()
                    else
                        uploadDataToServer(title, description, category, source, "")
                }

            }
        }
    }

    private fun uploadImageToImgur() {
        CoroutineScope(Dispatchers.IO).launch {
            val encodedString = async(Dispatchers.IO) {
                var encoded = ""
                try {
                    encoded = bitmap!!.convertBitmapTobase64()
                } catch (e: Exception) {
                    Log.e("Image Exception", e.toString())
                }
                encoded
            }.await()
            withContext(Dispatchers.Main) {
                viewModel.uploadImage(encodedString)
            }
        }
    }


    private fun uploadDataToServer(
        title: String,
        description: String,
        category: String,
        source: String,
        imageUrl: String
    ) {
        viewModel.createFeed(
            title, category, description,
            source, preference.getUsername(), imageUrl
        )
    }

    private fun checkInputValidations(
        title: String?, description: String?,
        category: String, source: String
    ): Boolean {
        if (title.isNullOrEmpty()) {
            this.showToast(resources.getString(R.string.empty_title))
            return false
        }

        if (description.isNullOrEmpty()) {
            this.showToast(resources.getString(R.string.empty_description))
            return false
        }

        if (category == "") {
            this.showToast(resources.getString(R.string.empty_category))
            return false
        }

        if (source.isNotEmpty() && !source.isValidUrl()) {
            this.showToast(resources.getString(R.string.invalid_url))
            return false
        }
        return true
    }

    private fun choosePhotoFromGallery() {
        val intent = Intent()
        intent.apply {
            type = FeedConstants.INTENT_TYPE_IMAGE
            action = Intent.ACTION_GET_CONTENT
        }

        startActivityForResult(
            Intent.createChooser(intent, FeedConstants.SELECT_FILE),
            FeedConstants.GALLERY_REQUEST_CODE
        )
    }


    private fun takePhotoFromCamera() {
        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File(getExternalFilesDir(null), "MyPhoto.jpg")
        cameraOutPutFileUri = Uri.fromFile(file)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraOutPutFileUri)
        startActivityForResult(intent, FeedConstants.CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                FeedConstants.GALLERY_PERMISSION_CODE -> choosePhotoFromGallery()
                FeedConstants.CAMERA_PERMISSION_CODE -> takePhotoFromCamera()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                FeedConstants.GALLERY_REQUEST_CODE -> {
                    val selectedImage: Uri? = data?.data
                    selectedImage?.let {
                        getAndSetImage(it)
                    }
                }
                FeedConstants.CAMERA_REQUEST_CODE -> {
                    imgFeed.visible()
                    txtImage.visible()
                    val uri: String = cameraOutPutFileUri.toString()

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(
                            this.contentResolver, cameraOutPutFileUri
                        )
                        val d: Drawable = BitmapDrawable(resources, bitmap)
                        imgFeed.setImageDrawable(d)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    /* val photo = data!!.extras!!["data"] as Bitmap?*/
                }
            }
        }
    }

    private fun getAndSetImage(uri: Uri) {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, uri)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (bitmap != null) {
            val outputDir: File = cacheDir
            val outputFile = File.createTempFile("image", ".jpg", outputDir)
            file = outputFile
            val stream = FileOutputStream(outputFile, false)
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.close()
        }
        imgFeed.visible()
        txtImage.visible()
        imgFeed.load(uri)

    }

    private fun setObservers() {
        viewModel.getCreateFeedLiveData().observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    this.showToast(resources.getString(R.string.hack_created))
                    progressBar.gone()
                    setResult(HomeConstants.CREATE_FEED_REQUEST_CODE)
                    finish()
                }
                Resource.Status.LOADING -> {
                    progressBar.visible()
                }
                Resource.Status.ERROR -> {
                    progressBar.gone()
                }
            }
        })

        viewModel.getImageUploadLiveData().observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    progressBar.gone()
                    this.showToast("Image Uploaded")
                    uploadDataToServer(
                        title,
                        description,
                        category,
                        source,
                        it?.data?.data?.link ?: ""
                    )
                }
                Resource.Status.LOADING -> {
                    progressBar.visible()
                }
                Resource.Status.ERROR -> {
                    progressBar.gone()
                }
            }
        })

        viewModel.getCategoriesLiveData().observe(this, Observer
        {
            when (it.status) {

                Resource.Status.LOADING -> {
                    binding.progressBar.visible()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    binding.layoutError.gone()
                    if (it.data != null) {
                        keywordsList.clear()
                        keywordsList.addAll(it.data.data)
                        setAdapter()
                    }
                }

                Resource.Status.ERROR -> {
                    when (it.throwable) {
                        is UnknownHostException -> {
                            binding.layoutError.txtError.text = "No Internet Connection !!"
                        }
                    }
                    binding.progressBar.gone()
                    binding.layoutError.visible()
                }
            }
        })
    }

}