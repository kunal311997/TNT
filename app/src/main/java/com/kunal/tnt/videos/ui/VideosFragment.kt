package com.kunal.tnt.videos.ui


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
import com.kunal.tnt.databinding.FragmentVideosBinding
import com.kunal.tnt.home.viewmodel.HomeViewModel
import com.kunal.tnt.videos.adapters.VideosAdapter
import com.kunal.tnt.videos.models.VideosResponse
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_videos.*
import javax.inject.Inject

//https://www.youtube.com/oembed?url=http://www.youtube.com/watch?v=2xsKNjCJWgE&format=json
class VideosFragment : DaggerFragment() {

    @Inject
    lateinit var adapter: VideosAdapter


    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory
    lateinit var binding: FragmentVideosBinding


    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProvidersFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel.getVideos()
        loadDummyDataForHomePage()
        initObservers()
    }

    private fun initObservers() {
        viewModel.getVideosLiveData().observe(requireActivity(), Observer
        {
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
    }

    private fun setAdapter(videosList: List<VideosResponse>) {
        rvVideos.adapter = adapter
        adapter.addItems(videosList)
    }

    private fun loadDummyDataForHomePage() {
        val data = Utilities.loadJSONFromAsset(requireActivity(), "dummy_videos_data.json")
        val videosList = object : TypeToken<List<VideosResponse?>?>() {}.type
        val videos: List<VideosResponse> = Gson().fromJson(data, videosList)
        Log.e("video", videos.toString())
        setAdapter(videos)
    }
}