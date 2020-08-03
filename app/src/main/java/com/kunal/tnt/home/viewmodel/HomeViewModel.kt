package com.kunal.tnt.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.createfeed.data.CreateFeedResponse
import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.repository.HomeRepository
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

    private val feedLiveData = MutableLiveData<Resource<List<Feed>>>()

    fun getFeedLiveData(): LiveData<Resource<List<Feed>>> {
        return feedLiveData
    }

    fun getFeed() {
        feedLiveData.value = Resource.loading(null)
        var feedResponse: Resource<List<Feed>>? = null

        viewModelScope.launch {
            withContext(ioDispatcher) {
                feedResponse = homeRepository.getFeed()
            }
            withContext(mainDispatcher) {
                feedResponse?.let {
                    feedLiveData.value = it
                }
            }
        }
    }


}