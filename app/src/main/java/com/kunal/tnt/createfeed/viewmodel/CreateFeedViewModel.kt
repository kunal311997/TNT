package com.kunal.tnt.createfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.createfeed.repository.CreateFeedRepository
import com.kunal.tnt.createfeed.data.CreateFeedResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class CreateFeedViewModel @Inject constructor(
    private val createFeedRepository: CreateFeedRepository,
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    @Named("MAIN") private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val createFeedData = MutableLiveData<Resource<CreateFeedResponse>>()

    fun getCreateFeedLiveData(): LiveData<Resource<CreateFeedResponse>> {
        return createFeedData
    }

    fun createFeed(title: String, keywords: String, description: String, file: File?) {
        createFeedData.value = Resource.loading(null)
        var createFeedResponse: Resource<CreateFeedResponse>? = null
        viewModelScope.launch {
            withContext(ioDispatcher) {
                createFeedResponse = createFeedRepository.createFeed(title, keywords, description, file)
            }
            withContext(mainDispatcher) {
                createFeedResponse?.let {
                    createFeedData.value = it
                }
            }
        }
    }
}