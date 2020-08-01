package com.kunal.tnt.home.network

import com.kunal.tnt.feed.data.FeedResponse
import retrofit2.http.GET

interface HomeApi {

    @GET("feed.php")
    suspend fun callFeedApi(): FeedResponse

}