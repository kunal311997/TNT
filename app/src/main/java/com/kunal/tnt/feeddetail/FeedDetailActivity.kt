package com.kunal.tnt.feeddetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kunal.tnt.R
import com.kunal.tnt.createfeed.utils.FeedConstants
import com.kunal.tnt.home.data.Feed
import kotlinx.android.synthetic.main.activity_feed_detail.*

class FeedDetailActivity : AppCompatActivity() {

    var adapter: FeedDetailAdapter? = null
    private var feedList: MutableList<Feed>? = null
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_detail)

        getIntentData()
        setAdapter()

        imgNext.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem + 1
        }
        imgPrevious.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private fun setAdapter() {
        adapter = feedList?.let { FeedDetailAdapter(it) }
        viewPager.adapter = adapter
        viewPager.setPadding(130, 0, 130, 0)
        viewPager.currentItem = position
    }

    private fun getIntentData() {
        feedList = intent.getSerializableExtra(FeedConstants.FEEDS_LIST) as MutableList<Feed>
        position = intent.getIntExtra(FeedConstants.POSITION, 0)
        adapter?.notifyDataSetChanged()

    }

}