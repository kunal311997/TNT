package com.kunal.tnt.createfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.tnt.categories.CategoriesResponse
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.createfeed.repository.CreateFeedRepository
import com.kunal.tnt.createfeed.data.CreateFeedResponse
import com.kunal.tnt.createfeed.data.ImageUploadResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class CreateFeedViewModel @Inject constructor(
    private val createFeedRepository: CreateFeedRepository,
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    @Named("MAIN") private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val createFeedData = MutableLiveData<Resource<CreateFeedResponse>>()
    private val imageUploadLiveData = MutableLiveData<Resource<ImageUploadResponse>>()
    private val categoriesLiveData = MutableLiveData<Resource<CategoriesResponse>>()

    fun getCreateFeedLiveData(): LiveData<Resource<CreateFeedResponse>> {
        return createFeedData
    }

    fun getImageUploadLiveData(): LiveData<Resource<ImageUploadResponse>> {
        return imageUploadLiveData
    }

    fun getCategoriesLiveData(): LiveData<Resource<CategoriesResponse>> {
        return categoriesLiveData
    }

    fun createFeed(
        title: String,
        category: String,
        description: String,
        source: String,
        createdBy: String,
        imageUrl: String
    ) {
        createFeedData.value = Resource.loading(null)
        var createFeedResponse: Resource<CreateFeedResponse>? = null
        viewModelScope.launch {
            withContext(ioDispatcher) {
                createFeedResponse = createFeedRepository.createFeed(
                    title,
                    category,
                    description,
                    source,
                    createdBy,
                    imageUrl
                )
            }
            withContext(mainDispatcher) {
                createFeedResponse?.let {
                    createFeedData.value = it
                }
            }
        }
    }

    fun uploadImage(file: String) {
        imageUploadLiveData.value = Resource.loading(null)
        var imageUploadResponse: Resource<ImageUploadResponse>? = null
        viewModelScope.launch {
            withContext(ioDispatcher) {
                imageUploadResponse = createFeedRepository.uploadImage(file)
            }
            withContext(mainDispatcher) {
                imageUploadResponse?.let {
                    imageUploadLiveData.value = it
                }
            }
        }
    }

    fun getCategories() {
        categoriesLiveData.value = Resource.loading(null)
        var feedResponse: Resource<CategoriesResponse>? = null

        viewModelScope.launch {
            withContext(ioDispatcher) {
                feedResponse = createFeedRepository.getCategories()
            }
            withContext(mainDispatcher) {
                feedResponse?.let {
                    categoriesLiveData.value = it
                }
            }
        }
    }
}