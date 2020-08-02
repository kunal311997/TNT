package com.kunal.tnt.createfeed.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.flexbox.FlexboxLayoutManager
import com.kunal.tnt.R
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.createfeed.adapter.KeywordsAdapter
import com.kunal.tnt.databinding.ActivityCreateFeedBinding
import com.kunal.tnt.feed.data.FeedType
import com.kunal.tnt.home.viewmodel.HomeViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_create_feed.*
import java.io.File
import java.io.InputStream
import javax.inject.Inject


class CreateFeedActivity : DaggerAppCompatActivity(), View.OnClickListener {

    val GALLERY_REQUEST_CODE = 1001

    private var file: File? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvidersFactory

    val viewmodel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[HomeViewModel::class.java]
    }

    private var feedType: FeedType = FeedType.TEXT

    private val keywordsList = arrayListOf(
        "Sports",
        "Fitness",
        "Education",
        "Technology",
        "Love",
        "Relationship",
        "Coaching",
        "Food",
        "Automobile"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCreateFeedBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_create_feed)

        //feedType = intent?.getSerializableExtra("Feed_Type") as FeedType
        addOnclickListeners()
        setObservers()


        val staggeredGridLayoutManager = FlexboxLayoutManager(this)
        binding.rvKeywords.layoutManager = staggeredGridLayoutManager
        val adapter = KeywordsAdapter(keywordsList)
        binding.rvKeywords.adapter = adapter

        if (feedType == FeedType.VIDEO)
            addTextChangeListener()
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!AccountManager.isUserLoggedIn(this) ){
            val intent = Intent(this, OnBoardingActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            return
        }
    }*/

    private fun addOnclickListeners() {
        imgFeed.setOnClickListener(this)
        btnSave.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            imgFeed -> {
                /*    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    startActivityForResult(intent, FILE_PICK)*/

                 */
                if (checkGalleryPermissions(this)) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(
                        permissions,
                        1001
                    )
                } else {
                    choosePhotoFromGallery()
                }

            }
            btnSave -> {

                viewmodel.createFeed(
                    edtTitle.text.toString(),
                    "fghsdf,dgfdfd",
                    edtDesc.text.toString(),
                     file
                )


            }
        }
    }

    private fun checkGalleryPermissions(context: FragmentActivity): Boolean {
        if (PermissionChecker.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_DENIED
        ) {
            return false
        }
        return true
    }

    private fun choosePhotoFromGallery() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, GALLERY_REQUEST_CODE)
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
                GALLERY_REQUEST_CODE -> {
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
        viewmodel.getCreateFeedLiveData().observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                    //hideLoading()
                }
                Resource.Status.LOADING -> {
                    //showLoading()
                }
            }
        })
    }

    private fun addTextChangeListener() {
        /*edtDesc.addTextChangedListener {
            *//*if (it?.contains("youtube")!!) {
                imgFeed.loadImage(Utilities.getThumbnailUrl(it.toString()))
            }*//*
        }*/
    }

}