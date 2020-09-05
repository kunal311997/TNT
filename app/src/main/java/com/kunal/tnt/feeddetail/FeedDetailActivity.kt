package com.kunal.tnt.feeddetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.common.uils.Utilities.openWebPage
import com.kunal.tnt.common.uils.Utilities.showToast
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.createfeed.utils.FeedConstants
import com.kunal.tnt.favourites.models.Favourites
import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.viewmodel.HomeViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_feed_detail.*
import javax.inject.Inject

class FeedDetailActivity : DaggerAppCompatActivity() {

    var feedAdapter: FeedDetailAdapter? = null
    private var feedList: MutableList<Feed>? = null
    var position = 0

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProvidersFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_detail)

        getIntentData()
        setAdapter()
        initClickListeners()
        initObservers()
    }

    private fun initClickListeners() {
        imgNext.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem + 1
        }
        imgPrevious.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private fun setAdapter() {
        feedAdapter = feedList?.let { FeedDetailAdapter(it) }
        viewPager.adapter = feedAdapter
        viewPager.setPadding(40, 0, 40, 0)
        viewPager.currentItem = position

        feedAdapter?.bookmarkListener = { item ->
            var favourites: Favourites
            item.apply {
                favourites = Favourites(
                    id, title, category, description,
                    source, createdBy, backgroundImage, createdAt
                )
            }
            if (item.isBookmarked) {
                viewModel.book(favourites)
                this.showToast(resources.getString(R.string.added_to_favourites))
            } else {
                viewModel.unBook(item.id)
                this.showToast(resources.getString(R.string.removed_favourites))
            }
        }

        feedAdapter?.shareListener = {
            val message =
                "Hey !! Please check this amazing post - \n\n" +
                        "${it.title} \n ${it.description} \n ${it.source} \n ${it.backgroundImage}"
            Utilities.showChooserForLinkShare(this, message)
        }

        feedAdapter?.openBrowserListener = {
            this.openWebPage(it.source)
        }
    }

    private fun getIntentData() {
        feedList = intent.getSerializableExtra(FeedConstants.FEEDS_LIST) as MutableList<Feed>
        position = intent.getIntExtra(FeedConstants.POSITION, 0)
        feedAdapter?.notifyDataSetChanged()

    }
    private fun initObservers() {
        viewModel.allFavourites.observe(this, Observer { dbList ->
            feedAdapter?.notifyDataSetChanged()
        })
    }
}