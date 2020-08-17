package com.kunal.tnt.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.tnt.categories.Categories
import com.kunal.tnt.categories.CategoriesResponse
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.favourites.models.Favourites
import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.repository.HomeRepository
import com.kunal.tnt.videos.models.VideosResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    @Named("MAIN") private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val feedLiveData = MutableLiveData<Resource<List<Feed>>>()
    private val videosLiveData = MutableLiveData<Resource<VideosResponse>>()
    private val categoriesLiveData = MutableLiveData<Resource<CategoriesResponse>>()
    private val feedByCategoryLiveData = MutableLiveData<Resource<List<Feed>>>()
    val allFavourites: LiveData<List<Favourites>> = homeRepository.allFavourites

    fun getFeedLiveData(): LiveData<Resource<List<Feed>>> {
        return feedLiveData
    }

    fun getVideosLiveData(): LiveData<Resource<VideosResponse>> {
        return videosLiveData
    }

    fun getCategoriesLiveData(): LiveData<Resource<CategoriesResponse>> {
        return categoriesLiveData
    }

    fun getFeedByCategoryLiveData(): LiveData<Resource<List<Feed>>> {
        return feedByCategoryLiveData
    }

    private val refreshFeed = MutableLiveData<Boolean>(false)

    fun refreshFeed(b: Boolean) {
        refreshFeed.value = b
    }

    fun isRefreshFeed(): LiveData<Boolean> {
        return refreshFeed
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

    fun getVideos() {
        videosLiveData.value = Resource.loading(null)
        var feedResponse: Resource<VideosResponse>? = null

        viewModelScope.launch {
            withContext(ioDispatcher) {
                feedResponse = homeRepository.getVideos()
            }
            withContext(mainDispatcher) {
                feedResponse?.let {
                    videosLiveData.value = it
                }
            }
        }
    }

    fun getCategories() {
        categoriesLiveData.value = Resource.loading(null)
        var feedResponse: Resource<CategoriesResponse>? = null

        viewModelScope.launch {
            withContext(ioDispatcher) {
                feedResponse = homeRepository.getCategories()
            }
            withContext(mainDispatcher) {
                feedResponse?.let {
                    categoriesLiveData.value = it
                }
            }
        }
    }

    fun getFeedByCategory(category: String) {
        feedByCategoryLiveData.value = Resource.loading(null)
        var feedResponse: Resource<List<Feed>>? = null

        viewModelScope.launch {
            withContext(ioDispatcher) {
                feedResponse = homeRepository.getFeedByCategoryApi(category)
            }
            withContext(mainDispatcher) {
                feedResponse?.let {
                    feedByCategoryLiveData.value = it
                }
            }
        }
    }

    fun book(favourites: Favourites) = viewModelScope.launch(Dispatchers.IO) {
        homeRepository.book(favourites)
    }

    fun unBook(userId: String) = viewModelScope.launch {
        withContext(ioDispatcher) {
            homeRepository.unBook(userId)
        }
    }

}