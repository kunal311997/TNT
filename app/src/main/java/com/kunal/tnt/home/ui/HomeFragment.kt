package com.kunal.tnt.home.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kunal.tnt.R
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.common.uils.Utilities.gone
import com.kunal.tnt.common.uils.Utilities.visible
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.createfeed.utils.FeedConstants
import com.kunal.tnt.databinding.FragmentHomeBinding
import com.kunal.tnt.favourites.models.Favourites
import com.kunal.tnt.feeddetail.FeedDetailActivity
import com.kunal.tnt.home.adapter.FeedsAdapter
import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.viewmodel.HomeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var feedAdapter: FeedsAdapter

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var binding: FragmentHomeBinding
    private val feedsList = ArrayList<Feed>()

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProvidersFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //viewModel.getFeed()
        loadDummyDataForHomePage()
        initObservers()
        initClickListeners()
    }

    private fun initClickListeners() {
        imgCompare.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getFeed()
    }

    private fun initObservers() {
        viewModel.getFeedLiveData().observe(requireActivity(), Observer {
            when (it.status) {

                Resource.Status.LOADING -> {
                    binding.progressBar.visible()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    if (it.data != null) {
                        setAdapter(it.data)
                        feedsList.clear()
                        feedsList.addAll(it.data)
                    }
                }

                Resource.Status.ERROR -> {
                    binding.progressBar.gone()
                }
            }
        })
        viewModel.allFavourites.observe(requireActivity(), Observer {
            Log.e("All", it.toString())

            feedsList.forEach { feed ->
                it.forEach { x ->
                    if (x.id == feed.id) {
                        feed.isBookmarked = true
                    }
                }
            }
            setAdapter(feedsList)

        })

        viewModel.isRefreshFeed().observe(requireActivity(), Observer {
            if (it) {
                viewModel.getFeed()
                viewModel.refreshFeed(false)
            }
        })
    }

    private fun setAdapter(data: List<Feed>) {
        binding.rvFeeds.adapter = feedAdapter
        feedAdapter.addItems(data)

        feedAdapter.listener = { _, _, pos ->
            val intent = Intent(requireActivity(), FeedDetailActivity::class.java)
            intent.putExtra(FeedConstants.FEEDS_LIST, feedsList)
            intent.putExtra(FeedConstants.POSITION, pos)
            startActivity(intent)
        }

        feedAdapter.bookmarkListener = { item ->
            var favourites: Favourites
            item.apply {
                favourites = Favourites(
                    id, title, category, description,
                    source, createdBy, backgroundImage, createdAt
                )
            }
            if (item.isBookmarked)
                viewModel.book(favourites)
            else viewModel.unBook(item.id)
        }

    }

    private fun loadDummyDataForHomePage() {
        val data = Utilities.loadJSONFromAsset(requireActivity(), "dummy_data.json")
        val feedList = object : TypeToken<List<Feed?>?>() {}.type
        val feeds: List<Feed> = Gson().fromJson(data, feedList)
        setAdapter(feeds)
        feedsList.clear()
        feedsList.addAll(feeds)
    }


    companion object {
        private var fragment: HomeFragment? = null
        fun getInstance(): HomeFragment? {
            if (fragment == null)
                fragment = HomeFragment()
            /*val args = Bundle()
            args.putInt("someInt", someInt)
            fragment!!.arguments = args*/
            return fragment
        }
    }

}