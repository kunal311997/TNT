package com.kunal.tnt.home.ui


import android.content.Intent
import android.os.Bundle
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
import com.kunal.tnt.databinding.FragmentHomeBinding
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
            val intent = Intent(requireActivity(), FeedDetailActivity::class.java)
            startActivity(intent)
        }
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
                    }
                }

                Resource.Status.ERROR -> {
                    binding.progressBar.gone()
                }
            }
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

        feedAdapter.listener = { _, _, _ ->
            val intent = Intent(requireActivity(), FeedDetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadDummyDataForHomePage() {
        val data = Utilities.loadJSONFromAsset(requireActivity(), "dummy_data.json")
        val feedList =
            object : TypeToken<List<Feed?>?>() {}.type
        val feeds: List<Feed> = Gson().fromJson(data, feedList)
        setAdapter(feeds)
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