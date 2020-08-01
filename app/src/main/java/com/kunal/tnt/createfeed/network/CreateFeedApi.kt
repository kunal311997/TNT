package com.kunal.tnt.createfeed.network

import com.kunal.tnt.createfeed.data.CreateFeedRequest
import com.kunal.tnt.createfeed.data.CreateFeedResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateFeedApi {

    @POST("feed.php")
    suspend fun createFeed(@Body request: CreateFeedRequest): CreateFeedResponse
}