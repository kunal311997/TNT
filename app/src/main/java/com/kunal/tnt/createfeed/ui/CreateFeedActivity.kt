package com.kunal.tnt.createfeed.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.flexbox.FlexboxLayoutManager
import com.kunal.tnt.R
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.SharedPrefClient
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.common.uils.Utilities.gone
import com.kunal.tnt.common.uils.Utilities.isValidUrl
import com.kunal.tnt.common.uils.Utilities.showToast
import com.kunal.tnt.common.uils.Utilities.visible
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.createfeed.adapter.KeywordsAdapter
import com.kunal.tnt.createfeed.data.Keywords
import com.kunal.tnt.createfeed.utils.FeedConstants
import com.kunal.tnt.createfeed.viewmodel.CreateFeedViewModel
import com.kunal.tnt.databinding.ActivityCreateFeedBinding
import com.kunal.tnt.home.utils.HomeConstants
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_create_feed.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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

    private val keywordsList = arrayListOf(
        Keywords("Sports"),
        Keywords("Fitness"),
        Keywords("Education"),
        Keywords("Technology"),
        Keywords("Love"),
        Keywords("Relationship"),
        Keywords("Coaching"),
        Keywords("Food"),
        Keywords("Automobile")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_feed)

        addOnclickListeners()
        setObservers()
        setAdapter()
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
            btnDone -> {
                var category = ""
                if (adapter.selectedPosition != -1) {
                    category = keywordsList[adapter.selectedPosition].name
                }
                val title = edtTitle.text.toString()
                val description = edtDesc.text.toString()
                val source = edtSource.text.toString()

                if (checkInputValidations(title, description, category, source)) {
                    viewModel.createFeed(
                        title, category, description,
                        source, preference.getUsername(), file
                    )
                }
            }
        }
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
            FeedConstants.IMAGE_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            choosePhotoFromGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FeedConstants.IMAGE_REQUEST_CODE -> {
                    val selectedImage: Uri? = data?.data
                    selectedImage?.let {
                        getAndSetImage(it)
                    }

                }
            }
        }
    }

    private fun getAndSetImage(uri: Uri) {
        var bitmap: Bitmap? = null
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
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
    }

}