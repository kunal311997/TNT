package com.kunal.tnt.home.repository

import androidx.lifecycle.LiveData
import com.kunal.tnt.categories.Categories
import com.kunal.tnt.categories.CategoriesResponse
import com.kunal.tnt.common.repository.BaseRepository
import com.kunal.tnt.common.data.Resource
import com.kunal.tnt.favourites.models.Favourites
import com.kunal.tnt.favourites.db.FavouritesDao
import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.network.HomeApi
import com.kunal.tnt.videos.models.VideosResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class HomeRepository @Inject constructor(
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    private val homeApi: HomeApi,
    private val favouritesDao: FavouritesDao
) : BaseRepository() {


    suspend fun getFeed(): Resource<List<Feed>> {
        return safeApiCall(ioDispatcher) { homeApi.callFeedApi() }
    }

    suspend fun getVideos(): Resource<VideosResponse> {
        return safeApiCall(ioDispatcher) { homeApi.callVideosApi() }
    }

    suspend fun getCategories(): Resource<CategoriesResponse> {
        return safeApiCall(ioDispatcher) { homeApi.callCategoriesApi() }
    }

    suspend fun getFeedByCategoryApi(category: String): Resource<List<Feed>> {
        return safeApiCall(ioDispatcher) { homeApi.callFeedByCategoryApi(category) }
    }

    suspend fun book(favourite: Favourites) {
        favouritesDao.insert(favourite)
    }

    suspend fun unBook(userId: String) {
        favouritesDao.deleteByUserId(userId)
    }

    val allFavourites: LiveData<List<Favourites>> = favouritesDao.getAllFavourites()

}