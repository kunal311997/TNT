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
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.common.uils.Utilities.gone
import com.kunal.tnt.common.uils.Utilities.openWebPage
import com.kunal.tnt.common.uils.Utilities.showToast
import com.kunal.tnt.common.uils.Utilities.visible
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.createfeed.utils.FeedConstants
import com.kunal.tnt.databinding.FragmentHomeBinding
import com.kunal.tnt.favourites.models.Favourites
import com.kunal.tnt.feeddetail.FeedDetailActivity
import com.kunal.tnt.home.adapter.FeedsAdapter
import com.kunal.tnt.home.adapter.ReposLoadStateAdapter
import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.viewmodel.HomeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.layout_error_page.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
    private var searchJob: Job? = null

    var hashMap = HashSet<String>()

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

        initObservers()
        initListeners()
        setAdapter()
        callFeedApi()
        initAdapterListeners()
        setLoadStateListener()
    }


    private fun setLoadStateListener() {
        feedAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.visible()
            } else {
                binding.progressBar.gone()
                binding.layoutError.gone()

                try {
                    if ((loadState.source.refresh as LoadState.Error).error is java.net.ConnectException) {
                        binding.layoutError.txtError.text = getString(R.string.no_internet)
                        binding.layoutError.visible()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun callFeedApi() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getFeeds().collectLatest {
                feedAdapter.submitData(it)
            }

        }
    }

    private fun setAdapter() {
        binding.rvFeeds.apply {
            adapter = feedAdapter
        }
        binding.rvFeeds.adapter = feedAdapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { feedAdapter.retry() },
            footer = ReposLoadStateAdapter { feedAdapter.retry() }
        )

    }

    private fun initListeners() {
        binding.layoutError.btRetry.setOnClickListener {
            feedAdapter.retry()
        }
    }

    private fun initObservers() {
        viewModel.allFavourites.observe(requireActivity(), Observer { dbList ->

            dbList.forEach { list ->
                hashMap.add(list.id)
            }
            feedAdapter.addDbHashMap(hashMap)
            feedAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapterListeners() {
        feedAdapter.listener = { _, _, pos ->
            val intent = Intent(requireActivity(), FeedDetailActivity::class.java)
            intent.putExtra(FeedConstants.FEEDS_LIST, viewModel.getTotalList())
            intent.putExtra(FeedConstants.POSITION, pos)
            startActivity(intent)
        }

        feedAdapter.bookmarkListener = { item, pos ->
            var favourites: Favourites
            item.apply {
                favourites = Favourites(
                    id, title, category, description,
                    source, createdBy, backgroundImage, createdAt
                )
            }
            if (item.isBookmarked) {
                viewModel.book(favourites)
                viewModel.getTotalList()[pos].isBookmarked = true
                requireActivity().showToast(resources.getString(R.string.added_to_favourites))
            } else {
                viewModel.unBook(item.id)
                viewModel.getTotalList()[pos].isBookmarked = true
                requireActivity().showToast(resources.getString(R.string.removed_favourites))
            }
        }

        feedAdapter.shareListener = {
            val message =
                "Hey !! Please check this amazing post - \n\n" +
                        "${it.title} \n ${it.description} \n ${it.source} \n ${it.backgroundImage}"
            Utilities.showChooserForLinkShare(requireActivity(), message)
        }

        feedAdapter.openBrowserListener = {
            requireActivity().openWebPage(it.source)
        }
    }

    private fun loadDummyDataForHomePage() {
        val data = Utilities.loadJSONFromAsset(requireActivity(), "dummy_data.json")
        val feedList = object : TypeToken<List<Feed?>?>() {}.type
        val feeds: List<Feed> = Gson().fromJson(data, feedList)
    }

}