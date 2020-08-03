package com.kunal.tnt.createfeed.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.flexbox.FlexboxLayoutManager
import com.kunal.tnt.R
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.common.uils.Utilities.hideProgressBar
import com.kunal.tnt.common.uils.Utilities.showProgressbar
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.createfeed.adapter.KeywordsAdapter
import com.kunal.tnt.createfeed.data.Keywords
import com.kunal.tnt.createfeed.utils.FeedConstants
import com.kunal.tnt.createfeed.viewmodel.CreateFeedViewModel
import com.kunal.tnt.databinding.ActivityCreateFeedBinding
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_create_feed.*
import java.io.File
import javax.inject.Inject


class CreateFeedActivity : DaggerAppCompatActivity(), View.OnClickListener {

    private var file: File? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvidersFactory

    @Inject
    lateinit var adapter: KeywordsAdapter

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

    }

    private fun setAdapter() {
        val staggeredGridLayoutManager = FlexboxLayoutManager(this)
        binding.rvKeywords.layoutManager = staggeredGridLayoutManager
        binding.rvKeywords.adapter = adapter
        adapter.addItems(keywordsList)


        adapter.listener = { view, _, pos ->

            if (keywordsList[pos].isSelected) {
                keywordsList[pos].isSelected = false
                view.background =
                    ContextCompat.getDrawable(this, R.drawable.bg_white_rounded_transparent)

            } else {
                keywordsList[pos].isSelected = true
                view.background =
                    ContextCompat.getDrawable(this, R.drawable.bg_accent_rounded)
            }
        }
    }


    private fun addOnclickListeners() {
        imgFeed.setOnClickListener(this)
        btnSave.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            imgFeed -> {
                if (Utilities.checkGalleryPermissions(this)) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(
                        permissions,
                        FeedConstants.GALLERY_PERMISSION_CODE
                    )
                } else {
                    choosePhotoFromGallery()
                }
            }
            btnSave -> {

                var keywords = ""
                keywordsList.forEach {
                    keywords += it.name + ", "
                }
                viewModel.createFeed(
                    edtTitle.text.toString(), keywords, edtDesc.text.toString(), file
                )
            }
        }
    }


    private fun choosePhotoFromGallery() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, FeedConstants.IMAGE_REQUEST_CODE)
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
                        //  file = File(selectedImage.path)
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
                    progressBar.hideProgressBar()
                    finish()
                }
                Resource.Status.LOADING -> {
                    progressBar.showProgressbar()
                }
                Resource.Status.ERROR -> {
                    progressBar.hideProgressBar()
                }
            }
        })
    }

}