package com.kunal.tnt.home.network

 import com.kunal.tnt.home.data.Feed
import retrofit2.http.GET

interface HomeApi {

    @GET("feed/getFeed")
    suspend fun callFeedApi(): List<Feed>

}