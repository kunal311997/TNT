package com.kunal.tnt.feed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kunal.tnt.R
import com.kunal.tnt.feed.adapter.FeedAdapter
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.home.viewmodel.HomeViewModel
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.databinding.FragmentFeedBinding
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject

class FeedFragmentNew : DaggerFragment() {

    @Inject
    lateinit var feedAdapter: FeedAdapter

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFeedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProvider(this, viewModelProvidersFactory)[HomeViewModel::class.java]

        viewModel.getFeed()
        viewModel.getFeedLiveData().observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    rvFeed.adapter = feedAdapter
                    if (it.data?.data != null)
                        feedAdapter.addItems(it.data.data)
                    feedAdapter.listener = { _, _, _ ->
                    }
                    //hideLoading()
                }
                Resource.Status.LOADING -> {
                    //showLoading()
                }
                else -> {

                }
            }
        })
    }
}