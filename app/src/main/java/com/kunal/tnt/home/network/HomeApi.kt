package com.kunal.tnt.home.network

import com.kunal.tnt.home.data.Feed
import com.kunal.tnt.home.utils.HomeConstants
import com.kunal.tnt.videos.models.VideosResponse
import retrofit2.http.GET

interface HomeApi {

    @GET(HomeConstants.GET_FEED)
    suspend fun callFeedApi(): List<Feed>

    @GET(HomeConstants.GET_VIDEOS)
    suspend fun callVideosApi(): List<VideosResponse>

}