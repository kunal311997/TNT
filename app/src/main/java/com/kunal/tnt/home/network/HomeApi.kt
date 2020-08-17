package com.kunal.tnt.home.network

import com.kunal.tnt.categories.Categories
import com.kunal.tnt.categories.CategoriesResponse
import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.utils.HomeConstants
import com.kunal.tnt.videos.models.VideosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET(HomeConstants.GET_FEED)
    suspend fun callFeedApi(): List<Feed>

    @GET(HomeConstants.GET_VIDEOS)
    suspend fun callVideosApi(): VideosResponse

    @GET(HomeConstants.GET_CATEGORIES)
    suspend fun callCategoriesApi(): CategoriesResponse

    @GET(HomeConstants.GET_FEED_BY_CATEGORY)
    suspend fun callFeedByCategoryApi(@Query("category") category: String): List<Feed>
}