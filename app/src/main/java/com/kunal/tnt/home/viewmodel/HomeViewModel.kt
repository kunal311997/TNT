package com.kunal.tnt.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.createfeed.data.CreateFeedResponse
import com.kunal.tnt.home.repository.HomeRepository
import com.kunal.tnt.feed.data.FeedResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    @Named("MAIN") private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val feedLiveData = MutableLiveData<Resource<FeedResponse>>()
    private val createFeedData = MutableLiveData<Resource<CreateFeedResponse>>()

    fun getFeedLiveData(): LiveData<Resource<FeedResponse>> {
        return feedLiveData
    }

    fun getCreateFeedLiveData(): LiveData<Resource<CreateFeedResponse>> {
        return createFeedData
    }

    fun getFeed() {
        feedLiveData.value = Resource.loading(null)
        var feedResponse: Resource<FeedResponse>? = null

        viewModelScope.launch {
            withContext(ioDispatcher) {
                //feedResponse = homeRepository.getFeed()
            }
            withContext(mainDispatcher) {
                feedResponse?.let {
                    feedLiveData.value = it
                }
            }
        }
    }

    fun createFeed(title: String, keywords: String, description: String, file: File?) {
        createFeedData.value = Resource.loading(null)
        var createFeedResponse: Resource<CreateFeedResponse>? = null
        viewModelScope.launch {
            withContext(ioDispatcher) {
                createFeedResponse = homeRepository.createFeed(title, keywords, description, file)
            }
            withContext(mainDispatcher) {
                createFeedResponse?.let {
                    createFeedData.value = it
                }
            }
        }
    }
}