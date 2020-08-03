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
import com.kunal.tnt.R
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.Utilities.hideProgressBar
import com.kunal.tnt.common.uils.Utilities.showProgressbar
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.databinding.FragmentHomeBinding
import com.kunal.tnt.home.adapter.FeedsAdapter
import com.kunal.tnt.home.viewmodel.HomeViewModel
import dagger.android.support.DaggerFragment
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

        viewModel.getFeed()

        viewModel.getFeedLiveData().observe(requireActivity(), Observer {
            when (it.status) {

                Resource.Status.LOADING -> {
                    binding.progressBar.showProgressbar()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.hideProgressBar()
                    binding.rvFeeds.adapter = feedAdapter
                    if (it.data != null)
                        feedAdapter.addItems(it.data)

                    feedAdapter.listener = { _, _, _ ->
                    }
                }

                Resource.Status.ERROR -> {
                    binding.progressBar.hideProgressBar()
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