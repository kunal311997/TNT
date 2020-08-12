package com.kunal.tnt.createfeed.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_feed)

        addOnclickListeners()
        setObservers()
        setAdapter()
        txtName.text = "By ${preference.getUsername()}"
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

                viewModel.createFeed(
                    edtTitle.text.toString(),
                    category,
                    edtDesc.text.toString(),
                    edtSource.text.toString(),
                    preference.getUsername(),
                    file
                )
            }
        }
    }


    private fun choosePhotoFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //

        startActivityForResult(
            Intent.createChooser(intent, "Select File"),
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


                        var bm: Bitmap? = null
                        if (data != null) {
                            try {
                                bm = MediaStore.Images.Media.getBitmap(
                                    applicationContext.contentResolver,
                                    data.data
                                )
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                        if (bm != null) { // sanity check
                            val outputDir: File = cacheDir // Activity context
                            val outputFile = File.createTempFile(
                                "image",
                                ".jpg",
                                outputDir
                            ) // follow the API for createTempFile
                            Log.e("file", outputFile.toString())
                            file = outputFile
                            val stream = FileOutputStream(
                                outputFile,
                                false
                            ) // Add false here so we don't append an image to another image. That would be weird.
                            // This line actually writes a bitmap to the stream. If you use a ByteArrayOutputStream, you end up with a byte array. If you use a FileOutputStream, you end up with a file.
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            stream.close() // cleanup
                        }

                        imgFeed.visible()
                        txtImage.visible()
                        imgFeed.load(selectedImage)
                    }
                }
            }
        }
    }

    private fun setObservers() {
        viewModel.getCreateFeedLiveData().observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(this, "Created Successfully", Toast.LENGTH_SHORT).show()
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