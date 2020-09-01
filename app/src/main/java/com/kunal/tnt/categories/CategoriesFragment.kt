package com.kunal.tnt.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kunal.tnt.R
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.Utilities.gone
import com.kunal.tnt.common.uils.Utilities.visible
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.createfeed.utils.FeedConstants
import com.kunal.tnt.databinding.FragmentCategoriesBinding
import com.kunal.tnt.enroll.adapters.SelectInterestsAdapter
import com.kunal.tnt.feeddetail.FeedDetailActivity
import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.viewmodel.HomeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.layout_error_page.view.*
import java.net.UnknownHostException
import javax.inject.Inject

class CategoriesFragment : DaggerFragment() {

    lateinit var binding: FragmentCategoriesBinding

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProvidersFactory)[HomeViewModel::class.java]
    }

    @Inject
    lateinit var adapter: SelectInterestsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getCategories()
        //loadDummyDataForHomePage()
        initObservers()
        initListeners()
    }


    private fun initListeners() {
        binding.layoutError.btRetry.setOnClickListener {
            viewModel.getCategories()
        }
    }

    private fun initObservers() {
        viewModel.getCategoriesLiveData().observe(requireActivity(), Observer
        {
            when (it.status) {

                Resource.Status.LOADING -> {
                    binding.progressBar.visible()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    binding.layoutError.gone()
                    if (it.data != null) {
                        setAdapter(it.data.data)
                    }
                }

                Resource.Status.ERROR -> {
                    when (it.throwable) {
                        is UnknownHostException -> {
                            binding.layoutError.txtError.text = "No Internet Connection !!"
                        }
                    }
                    binding.progressBar.gone()
                    binding.layoutError.visible()
                }
            }
        })

        viewModel.getFeedByCategoryLiveData().observe(requireActivity(), Observer
        {
            when (it.status) {

                Resource.Status.LOADING -> {
                    binding.progressBar.visible()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    binding.layoutError.gone()
                    if (it.data != null) {
                        val feedsList = ArrayList<Feed>()
                        feedsList.addAll(it.data)
                        val intent = Intent(requireActivity(), FeedDetailActivity::class.java)
                        intent.putExtra(FeedConstants.FEEDS_LIST, feedsList)
                        startActivity(intent)
                    }
                }

                Resource.Status.ERROR -> {
                    when (it.throwable) {
                        is UnknownHostException -> {
                            binding.layoutError.txtError.text = "No Internet Connection !!"
                        }
                    }
                    binding.progressBar.gone()
                    binding.layoutError.visible()
                }
            }
        })
    }

    private fun setAdapter(videosList: List<Categories>) {
        rvCategories.adapter = adapter
        adapter.addItems(videosList)

        adapter.listener = { view, item, pos ->
            viewModel.getFeedByCategory(item.categoryName)
        }
    }

    private fun loadDummyDataForHomePage() {
        /*  val data = Utilities.loadJSONFromAsset(requireActivity(), "dummy_videos_data.json")
          val videosList = object : TypeToken<List<VideosResponse?>?>() {}.type
          val videos: List<VideosResponse> = Gson().fromJson(data, videosList)
          Log.e("video", videos.toString())
          setAdapter(videos)*/
    }
}