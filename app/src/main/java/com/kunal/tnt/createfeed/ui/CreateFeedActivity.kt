package com.kunal.tnt.createfeed.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.flexbox.FlexboxLayoutManager
import com.kunal.tnt.R
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.createfeed.adapter.KeywordsAdapter
import com.kunal.tnt.feed.data.FeedType
import com.kunal.tnt.home.viewmodel.HomeViewModel
import com.kunal.tnt.databinding.ActivityCreateFeedBinding
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_create_feed.*
import javax.inject.Inject


class CreateFeedActivity : DaggerAppCompatActivity(), View.OnClickListener {

    val FILE_PICK = 1001

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvidersFactory

    val viewmodel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[HomeViewModel::class.java]
    }

    private var feedType: FeedType = FeedType.TEXT

    private val keywordsList = listOf(
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

        feedType = intent?.getSerializableExtra("Feed_Type") as FeedType
        addOnclickListeners()
        setObservers()


        val staggeredGridLayoutManager = FlexboxLayoutManager(this)
        rvKeywords.layoutManager = staggeredGridLayoutManager
        rvKeywords.adapter = KeywordsAdapter(keywordsList)


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
        btnDone.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            imgFeed -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, FILE_PICK)
            }
            btnDone -> {
                /* if (AccountManager.isUserLoggedIn(this)) {
                     viewmodel.createFeed(CreateFeedRequest("", "default", edtDesc.text.toString(), AccountManager.getUserDetails()?.displayName!!, feedType, AccountManager.getUserId()!!, ""))
                 }*/
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != FILE_PICK) {
            return
        }
        if (resultCode != RESULT_OK) {
            return
        }
        val returnUri = data?.data
        imgFeed.load(returnUri)
        //requestManager?.load(returnUri)?.into(imgFeed)
    }
}